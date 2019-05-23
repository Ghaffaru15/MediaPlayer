package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainController{

	@FXML private MediaView mv;
	@FXML private Button vid;
	@FXML Slider volume;
	
	
	private MediaPlayer mp;
	private Media me;
	private File selectedFile;
	
	public void UploadVid(ActionEvent event)
	{
		FileChooser fc = new FileChooser();
		
		fc.getExtensionFilters().addAll(new ExtensionFilter("mp4", "*.mp4"));	
		
		selectedFile = fc.showOpenDialog(null);
			
		if (selectedFile != null)
		{
			String path = selectedFile.getAbsolutePath();
			me = new Media(new File(path).toURI().toString());
			
			mp = new MediaPlayer(me);
			
			mv.setMediaPlayer(mp);
			DoubleProperty width = mv.fitWidthProperty();
			DoubleProperty height = mv.fitHeightProperty();
			
			width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
			height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));
			
			volume.setValue(mp.getVolume() * 100);
			volume.valueProperty().addListener(new InvalidationListener() {

				@Override
				public void invalidated(Observable observable) {
					// TODO Auto-generated method stub
					mp.setVolume(volume.getValue() / 100);
				}
				
		    });
		
		}
	}
	

	public void play(ActionEvent event)
	{
		mp.play();
		mp.setRate(1);
	}
	
	public void pause(ActionEvent event)
	{
		mp.pause();
	}
	
	public void fast(ActionEvent event)
	{
		mp.setRate(2);
	}
	
	public void slow(ActionEvent event)
	{
		mp.setRate(0.5);
	}	
	
	public void reload(ActionEvent event)
	{
		mp.seek(mp.getStartTime());
		mp.play();
	}
	
	public void start(ActionEvent event)
	{
		mp.seek(mp.getStartTime());
		mp.stop();
	}
	
	public void stop(ActionEvent event)
	{
		mp.seek(mp.getTotalDuration());
		mp.stop();
	}
}
