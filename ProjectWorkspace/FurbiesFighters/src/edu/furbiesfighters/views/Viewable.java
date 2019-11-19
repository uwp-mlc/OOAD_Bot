/**
 * 
 */
package edu.furbiesfighters.views;

import edu.furbiesfighters.gameplay.Main;
import edu.furbiesfighters.gameplay.Referee;

/**
 * @author Furbies Fighters
 *
 */
public interface Viewable {

	/**
	 * Initialize all view components. 
	 */
	public void setUpView();
	
	/**
	 * set the mainApp reference 
	 * @param mainApp
	 */
	public void setMainApp(Main mainApp);
	
	/**
	 * Sets the referee reference to the presenters
	 * @param ref
	 */
	public void setReferee(Referee ref);
}
