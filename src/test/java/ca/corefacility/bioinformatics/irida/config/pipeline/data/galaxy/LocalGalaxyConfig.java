package ca.corefacility.bioinformatics.irida.config.pipeline.data.galaxy;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;

import com.github.jmchilton.blend4j.galaxy.GalaxyInstanceFactory;
import com.github.jmchilton.galaxybootstrap.BootStrapper;
import com.github.jmchilton.galaxybootstrap.BootStrapper.GalaxyDaemon;
import com.github.jmchilton.galaxybootstrap.DownloadProperties;
import com.github.jmchilton.galaxybootstrap.GalaxyData;
import com.github.jmchilton.galaxybootstrap.GalaxyProperties;
import com.github.jmchilton.galaxybootstrap.GalaxyData.User;

import ca.corefacility.bioinformatics.irida.exceptions.galaxy.GalaxyConnectException;
import ca.corefacility.bioinformatics.irida.model.upload.galaxy.GalaxyAccountEmail;
import ca.corefacility.bioinformatics.irida.pipeline.upload.Uploader;
import ca.corefacility.bioinformatics.irida.pipeline.upload.galaxy.GalaxyAPI;
import ca.corefacility.bioinformatics.irida.pipeline.upload.galaxy.GalaxyUploader;
import ca.corefacility.bioinformatics.irida.pipeline.upload.galaxy.integration.LocalGalaxy;

/**
 * Builds a local running instance of Galaxy (requires mercurial and python) for integration testing.
 * @author Aaron Petkau <aaron.petkau@phac-aspc.gc.ca>
 *
 */
@Configuration
@Profile("test")
public class LocalGalaxyConfig {
	private static final Logger logger = LoggerFactory
			.getLogger(LocalGalaxyConfig.class);

	private static final int largestPort = 65535;

	/**
	 * Builds a GalaxyUploader to connect to a running instance of Galaxy.
	 * @return  An Uploader connected to a running instance of Galaxy.
	 * @throws MalformedURLException  If there was an issue when contructing a URL.
	 * @throws GalaxyConnectException If there was an issue connecting to the running instance of Galaxy.
	 */
	@Lazy
	@Bean
	public Uploader galaxyUploader() throws MalformedURLException, GalaxyConnectException {
		GalaxyUploader galaxyUploader = new GalaxyUploader();
		galaxyUploader.setupGalaxyAPI(localGalaxy().getGalaxyURL(),
				localGalaxy().getAdminName(), localGalaxy().getAdminAPIKey());

		return galaxyUploader;
	}

	/**
	 * Builds a GalaxyAPI object to connect to a running instance of Galaxy.
	 * @return  A GalaxyAPI object connected to a running instance of Galaxy.
	 * @throws MalformedURLException  If there was an issue building some of the URLs.
	 * @throws GalaxyConnectException If there was an issue connecting to the Galaxy instance.
	 */
	@Lazy
	@Bean
	public GalaxyAPI galaxyAPI() throws MalformedURLException, GalaxyConnectException {
		return new GalaxyAPI(localGalaxy().getGalaxyURL(), localGalaxy()
				.getAdminName(), localGalaxy().getAdminAPIKey());
	}

	/**
	 * Builds a new LocalGalaxy allowing for connecting with a running Galaxy instance.
	 * @return  A LocalGalaxy with information about the running Galaxy instance.
	 * @throws MalformedURLException  If there was an issue constructing the URLs.
	 */
	@Lazy
	@Bean
	public LocalGalaxy localGalaxy() throws MalformedURLException {
		LocalGalaxy localGalaxy = new LocalGalaxy();

		String randomPassword = UUID.randomUUID().toString();

		localGalaxy.setAdminName(new GalaxyAccountEmail("admin@localhost"));
		localGalaxy.setAdminPassword(randomPassword);
		localGalaxy.setUser1Name(new GalaxyAccountEmail("user1@localhost"));
		localGalaxy.setUser1Password(randomPassword);
		localGalaxy.setUser2Name(new GalaxyAccountEmail("user2@localhost"));
		localGalaxy.setUser2Password(randomPassword);
		localGalaxy.setNonExistentGalaxyAdminName(new GalaxyAccountEmail(
				"admin_no_exist@localhost"));
		localGalaxy.setNonExistentGalaxyUserName(new GalaxyAccountEmail(
				"no_exist@localhost"));

		localGalaxy.setInvalidGalaxyUserName(new GalaxyAccountEmail(
				"<a href='localhost'>invalid user</a>"));

		GalaxyData galaxyData = new GalaxyData();

		BootStrapper bootStrapper = downloadGalaxy(localGalaxy);
		localGalaxy.setBootStrapper(bootStrapper);

		GalaxyProperties galaxyProperties = setupGalaxyProperties(localGalaxy);
		localGalaxy.setGalaxyProperties(galaxyProperties);

		buildGalaxyUsers(galaxyData, localGalaxy);

		GalaxyDaemon galaxyDaemon = runGalaxy(galaxyData, localGalaxy);
		localGalaxy.setGalaxyDaemon(galaxyDaemon);

		localGalaxy.setGalaxyInstanceAdmin(GalaxyInstanceFactory.get(
				localGalaxy.getGalaxyURL().toString(),
				localGalaxy.getAdminAPIKey()));
		localGalaxy.setGalaxyInstanceUser1(GalaxyInstanceFactory.get(
				localGalaxy.getGalaxyURL().toString(),
				localGalaxy.getUser1APIKey()));
		localGalaxy.setGalaxyInstanceUser2(GalaxyInstanceFactory.get(
				localGalaxy.getGalaxyURL().toString(),
				localGalaxy.getUser2APIKey()));

		return localGalaxy;
	}

	/**
	 * Downloads the latest stable release of Galaxy.
	 * @param localGalaxy  The LocalGalaxy object used to fill in information about Galaxy.
	 * @return  A BootStrapper object describing the downloaded Galaxy.
	 */
	private BootStrapper downloadGalaxy(LocalGalaxy localGalaxy) {
		@SuppressWarnings("deprecation")
		DownloadProperties downloadProperties = new DownloadProperties(
				DownloadProperties.GALAXY_CENTRAL_REPOSITORY_URL,
				DownloadProperties.BRANCH_STABLE, null);
		BootStrapper bootStrapper = new BootStrapper(downloadProperties);

		File galaxyCache = new File(System.getProperty("user.home"),
				".galaxy-bootstrap");

		logger.info("About to download Galaxy from url: "
				+ DownloadProperties.GALAXY_CENTRAL_REPOSITORY_URL
				+ ", branch:" + DownloadProperties.BRANCH_STABLE);
		logger.info("Galaxy will be downloaded to cache at: "
				+ galaxyCache.getAbsolutePath() + ", and copied to: "
				+ bootStrapper.getPath());
		bootStrapper.setupGalaxy();
		logger.info("Finished downloading Galaxy");

		return bootStrapper;
	}

	/**
	 * Does some custom configuration for Galaxy to work with the tests.
	 * @param localGalaxy  The object describing the local running instance of Galaxy.
	 * @return  A GalaxyProperties object defining properties of the running instance of Galaxy.
	 * @throws MalformedURLException  If there was an issue constructing the Galaxy URL.
	 */
	private GalaxyProperties setupGalaxyProperties(LocalGalaxy localGalaxy)
			throws MalformedURLException {
		GalaxyProperties galaxyProperties = new GalaxyProperties()
				.assignFreePort().configureNestedShedTools();
		galaxyProperties.prepopulateSqliteDatabase();
		galaxyProperties.setAppProperty("allow_library_path_paste", "true");

		int galaxyPort = galaxyProperties.getPort();
		URL galaxyURL = new URL("http://localhost:" + galaxyPort + "/");
		localGalaxy.setGalaxyURL(galaxyURL);

		// set wrong port to something Galaxy is not running on
		int wrongPort = (galaxyPort + 1);
		if (wrongPort > largestPort) {
			wrongPort = galaxyPort - 1;
		}
		URL wrongGalaxyURL = new URL("http://localhost:" + wrongPort + "/");
		localGalaxy.setInvalidGalaxyURL(wrongGalaxyURL);

		return galaxyProperties;
	}

	/**
	 * Configures the users for the Galaxy for integration testing.
	 * @param galaxyData  A GalaxyData object used to setup users.
	 * @param localGalaxy  An object containing information about the local running Galaxy.
	 */
	private void buildGalaxyUsers(GalaxyData galaxyData, LocalGalaxy localGalaxy) {
		GalaxyProperties galaxyProperties = localGalaxy.getGalaxyProperties();

		User adminUser = new User(localGalaxy.getAdminName().getName());
		adminUser.setPassword(localGalaxy.getAdminPassword());
		localGalaxy.setAdminAPIKey(adminUser.getApiKey());

		User user1 = new User(localGalaxy.getUser1Name().getName());
		user1.setPassword(localGalaxy.getUser1Password());
		localGalaxy.setUser1APIKey(user1.getApiKey());

		User user2 = new User(localGalaxy.getUser2Name().getName());
		user2.setPassword(localGalaxy.getUser2Password());
		localGalaxy.setUser2APIKey(user2.getApiKey());

		galaxyData.getUsers().add(adminUser);
		galaxyData.getUsers().add(user1);
		galaxyData.getUsers().add(user2);

		galaxyProperties.setAdminUser(adminUser.getUsername());
	}

	/**
	 * Constructs a string containing the Galaxy user information for logging.
	 * @param usertype  The type of user constructed (admin, etc).
	 * @param name  The name of the user.
	 * @param password  The password of the user.
	 * @param apiKey  The api key of the user.
	 * @return  A String with the proper logging message.
	 */
	private String generateUserString(String usertype, String name,
			String password, String apiKey) {
		return "Galaxy " + usertype + " user: " + name + ", password: "
				+ password + ", apiKey: " + apiKey;
	}

	/**
	 * Runs the downloaded and configured instance of Galaxy.
	 * @param galaxyData  The data used to run Galaxy.
	 * @param localGalaxy  The object containing information about the local Galaxy instance.
	 * @return  A GalaxyDaemon object containing information about the running Galaxy process.
	 */
	private GalaxyDaemon runGalaxy(GalaxyData galaxyData,
			LocalGalaxy localGalaxy) {
		GalaxyDaemon galaxyDaemon;

		GalaxyProperties galaxyProperties = localGalaxy.getGalaxyProperties();
		BootStrapper bootStrapper = localGalaxy.getBootStrapper();

		File galaxyLogFile = new File(bootStrapper.getPath() + File.separator
				+ "paster.log");

		logger.info("Setting up Galaxy");
		logger.debug(generateUserString("admin", localGalaxy.getAdminName()
				.getName(), localGalaxy.getAdminPassword(), localGalaxy
				.getAdminAPIKey()));
		logger.debug(generateUserString("user1", localGalaxy.getUser1Name()
				.getName(), localGalaxy.getUser1Password(), localGalaxy
				.getUser1APIKey()));
		logger.debug(generateUserString("user2", localGalaxy.getUser2Name()
				.getName(), localGalaxy.getUser2Password(), localGalaxy
				.getUser2APIKey()));

		galaxyDaemon = bootStrapper.run(galaxyProperties, galaxyData);

		logger.info("Waiting for Galaxy to come up on url: "
				+ localGalaxy.getGalaxyURL() + ", log: "
				+ galaxyLogFile.getAbsolutePath());

		if (!galaxyDaemon.waitForUp()) {
			System.err.println("Could not launch Galaxy on "
					+ localGalaxy.getGalaxyURL());
			System.exit(1);
		}
		logger.info("Galaxy running on url: " + localGalaxy.getGalaxyURL());

		return galaxyDaemon;
	}
}