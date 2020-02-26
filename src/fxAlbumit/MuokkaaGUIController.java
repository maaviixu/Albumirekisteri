package fxAlbumit;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;

/**
 * @author maaviixu
 * @version 30.1.2020
 *
 */
public class MuokkaaGUIController implements ModalControllerInterface<String> {

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
	 void handleTallenna() {
		 tallenna();
	 }
	
	@FXML
	void handleLisaaKappale() {
		lisaaKappale();
	}
	
	@FXML
	void handleKumoa() {
		kumoa();
	}
      
	
	//==============================================
	
	private void tallenna() {
		 Dialogs.showMessageDialog("Tallennetaan! Mutta ei toimi viel�");
	 }
	
	private void lisaaKappale() {
		 Dialogs.showMessageDialog("Lis�t��n slotti uudelle kappaleelle! Mutta ei toimi viel�");
	}
	
	private void kumoa() {
		 Dialogs.showMessageDialog("Kumotaan muokkaukset! Mutta ei toimi viel�");

	}

	
	
}
