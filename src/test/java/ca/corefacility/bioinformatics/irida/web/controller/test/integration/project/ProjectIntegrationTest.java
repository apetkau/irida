package ca.corefacility.bioinformatics.irida.web.controller.test.integration.project;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.path.json.JsonPath.from;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import ca.corefacility.bioinformatics.irida.web.controller.test.listeners.IntegrationTestListener;

import com.google.common.net.HttpHeaders;
import com.google.common.net.MediaType;
import com.jayway.restassured.response.Response;

/**
 * Integration tests for projects.
 * 
 * @author Franklin Bristow <franklin.bristow@phac-aspc.gc.ca>
 */
public class ProjectIntegrationTest {

	private static final String PROJECTS = "/projects";

	/**
	 * If I try to issue a create request for an object with an invalid field
	 * name, the server should respond with 400.
	 */
	@Test
	public void testCreateProjectBadFieldName() {
		Response r = given().body("{ \"projectName\": \"some stupid project\" }").expect().response()
				.statusCode(HttpStatus.BAD_REQUEST.value()).when().post(PROJECTS);
		assertTrue(r.getBody().asString().contains("Unrecognized property [projectName]"));
	}

	/**
	 * Field names should be quoted. We should handle that failure gracefully.
	 */
	@Test
	public void testCreateProjectNoQuotes() {
		Response r = given().body("{ name: \"some stupid project\" }").expect().response()
				.statusCode(HttpStatus.BAD_REQUEST.value()).when().post(PROJECTS);
		assertTrue(r.getBody().asString().contains("double quotes"));
	}

	@Test
	public void testCreateProject() {
		Map<String, String> project = new HashMap<>();
		project.put("name", "new project");

		Response r = given().body(project).expect().response().statusCode(HttpStatus.CREATED.value()).when()
				.post(PROJECTS);
		String location = r.getHeader(HttpHeaders.LOCATION);
		assertNotNull(location);
		assertTrue(location.startsWith("http://localhost:8080/projects/"));
		String responseBody = get(location).asString();
		String projectUsersLocation = from(responseBody).get("resource.links.find{it.rel=='project/users'}.href");
		// confirm that the current user was added to the project.
		expect().body("resource.resources.username", hasItem(IntegrationTestListener.USERNAME)).when()
				.get(projectUsersLocation);
	}

	@Test
	public void testGetProject() {
		Map<String, String> project = new HashMap<>();
		String projectName = "new project";
		project.put("name", projectName);
		Response r = given().body(project).post(PROJECTS);
		String location = r.getHeader(HttpHeaders.LOCATION);
		expect().body("resource.name", equalTo(projectName)).and()
				.body("resource.links.rel", hasItems("self", "project/users", "project/samples")).when().get(location);
	}

	@Test
	public void testUpdateProjectName() {
		Map<String, String> project = new HashMap<>();
		String projectName = "new project";
		String updatedName = "updated new project";
		project.put("name", projectName);
		Response r = given().body(project).post(PROJECTS);
		String location = r.getHeader(HttpHeaders.LOCATION);
		project.put("name", updatedName);
		given().body(project).expect().statusCode(HttpStatus.OK.value()).when().patch(location);
		expect().body("resource.name", equalTo(updatedName)).when().get(location);
	}

	@Test
	public void testGetProjects() {
		// first page shouldn't have prev link, default view returns 20 projects
		expect().body("resource.links.rel", hasItems("self", "first", "next", "last")).and()
				.body("resource.links.rel", not(hasItem("prev"))).and()
				.body("resource.totalResources", isA(Integer.class)).when().get(PROJECTS);
	}

	@Test
	public void testDeleteProject() {
		String projectUri = "http://localhost:8080/projects/99";
		expect().body("resource.links.rel", hasItems("collection")).and()
				.body("resource.links.href", hasItems("http://localhost:8080/projects")).when().delete(projectUri);
	}

	/**
	 * Make sure that if we issue a HEAD request on a resource, the request
	 * succeeds. We have two valid endpoints where a client can get data from
	 * (provided that they use the correct Accept headers). Make sure that both
	 * work.
	 */
	@Test
	public void verifyExistenceOfProjectWithHEAD() {
		String projectUri = "http://localhost:8080/projects/3";
		expect().statusCode(HttpStatus.OK.value()).when().head(projectUri);
		given().header("Accept", MediaType.JSON_UTF_8.toString()).expect().statusCode(HttpStatus.OK.value()).when()
				.head(projectUri);
	}
}
