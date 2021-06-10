package mc322.lab07.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import mc322.lab07.Shroomite;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        // config.useGL30 = true;
        config.height = 800;
        config.width = 800;
		new LwjglApplication(new Shroomite(), config);
	}
}
