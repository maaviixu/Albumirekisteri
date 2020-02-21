package fxAlbumit;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;

/**
 * @author maaviixu
 * @version 30.1.2020
 *
 */
public class TietojaGUIController implements ModalControllerInterface<String> {

	@Override
	public String getResult() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void handleShown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDefault(String arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	public void handleSulje() {
		sulje();
	}
      //=============
	
	private void sulje() {
		 Dialogs.showMessageDialog("Suljetaan ikkuna! Mutta ei toimi vielä");
	}
}
