package webscraper;

import java.util.concurrent.ConcurrentHashMap;

import com.jcabi.xml.XMLDocument;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;

public class WebScrapingEngine extends Application {

	static ConcurrentHashMap<String, String> pageResults = new ConcurrentHashMap<>();

	@FunctionalInterface
	static interface PageLoadDone {
		boolean pageLoaded(String document);
	}

	public static class Fetcher implements Runnable {

		private String url;
		private PageLoadDone pld;
		private WebEngine eng;

		public boolean locked;

		public Fetcher(String url, PageLoadDone pld) {
			this.url = url;
			this.pld = pld;
		}

		@Override
		public void run() {
			eng = new WebEngine();
			eng.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
				public void changed(ObservableValue<? extends State> ov, State oldState, State newState) {
					if (newState == State.SUCCEEDED) {
						String str = new XMLDocument(eng.getDocument()).toString();

						if (pld.pageLoaded(str)) {
							pageResults.put(url, str);
							locked = false;
						}
					}
				}
			});

			eng.load(url);
		}
	}

	public static String getUrl(String url, PageLoadDone pld) throws InterruptedException {

		Fetcher fetcher = new Fetcher(url, pld);
		fetcher.locked = true;

		Platform.runLater(fetcher);

		while (fetcher.locked) {
			Thread.sleep(100);
		}

		return pageResults.get(url);
	}

	@Override
	public void start(Stage arg0) throws Exception {
	}

	/**
	 * Launch the FX application
	 */
	public static void launchIt(String[] args) {
		launch(args);
	}
}
