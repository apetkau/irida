package ca.nml.oltutest.controller;

import java.net.MalformedURLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ca.corefacility.bioinformatics.irida.exceptions.IridaOAuthException;
import ca.corefacility.bioinformatics.irida.model.RemoteAPI;
import ca.corefacility.bioinformatics.irida.repositories.remote.ProjectRemoteRepository;
import ca.corefacility.bioinformatics.irida.repositories.remote.model.RemoteProject;
import ca.corefacility.bioinformatics.irida.service.RemoteAPIService;

@Controller
@Scope("session")
public class ProjectsController {
	private ProjectRemoteRepository repo; //the repository we're communicating with
	private RemoteAPIService apiService; //a service to read information about remote apis
	private OltuAuthorizationController authController; //a reference to the authorization controller
	
	@Autowired
	public ProjectsController(ProjectRemoteRepository repo,OltuAuthorizationController authController,RemoteAPIService apiRepo){
		this.repo = repo;
		this.authController = authController;
		this.apiService = apiRepo;
	}
	
	/**
	 * Get a list of projects for a given service
	 * @param apiId the ID of the {@link RemoteAPI} we're talking to
	 * @return A model for a list of projects
	 * @throws Exception
	 */
	@RequestMapping("/remote/{remoteId}/projects")
	public ModelAndView getData(@PathVariable("remoteId") Long apiId){
		
		RemoteAPI remoteAPI = apiService.read(apiId);
		repo.setRemoteAPI(remoteAPI);
		
		List<RemoteProject> list = repo.list();

		ModelAndView modelAndView = new ModelAndView("data");
		modelAndView.addObject("service",remoteAPI);
		modelAndView.addObject("data", list);
		modelAndView.addObject("apiId",apiId);
		return modelAndView;
	}
	
	/**
	 * Get a specific project
	 * @param id the ID of the project to read
	 * @param apiId the id of the API to communicate with
	 * @return a modlel for a project
	 */
	@RequestMapping("/remote/{remoteId}/projects/{id}")
	public ModelAndView readData(@PathVariable("id") Long id,@PathVariable("remoteId") Long apiId){
		
		RemoteAPI remoteAPI = apiService.read(apiId);
		repo.setRemoteAPI(remoteAPI);
		
		System.out.println("reading " + id);
		RemoteProject read = repo.read(id);

		ModelAndView modelAndView = new ModelAndView("onedata");
		modelAndView.addObject("service",remoteAPI);
		modelAndView.addObject("data", read);
		modelAndView.addObject("apiId",id);
		return modelAndView;
	}
	
	
	@ExceptionHandler(IridaOAuthException.class)
	public ModelAndView handleOAuthException(HttpServletRequest request,IridaOAuthException ex) throws OAuthSystemException, MalformedURLException{
		String requestURI = request.getRequestURI();

		return authController.authenticate(ex.getRemoteAPI(), requestURI);
	}
}
