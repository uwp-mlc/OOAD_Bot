/**
 * 
 */
package edu.furbiesfighters.presenters;

import edu.furbiesfighters.gameplay.Main;
import edu.furbiesfighters.gameplay.Referee;

/**
 * @author Furbies Fighters
 * Inteface for encapsulating the data related to a presenter.
 */
public interface Presentable {

	/**
	 * Set the main app reference. 
	 * @param mainApp
	 */
	public void setMainApp(Main mainApp);
	
	/**
	 * Initialize the view componente
	 */
	public void initializeView();
	
	/**
	 * Sets the referee.
	 * @param ref
	 */
	public void setRef(Referee ref);
}
