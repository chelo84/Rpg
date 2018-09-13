package rpg;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import rpg.GUI.telainicio.TelaInicio;


public class Main {
    public static void main(String[] args) {
        UIManager.put("ProgressBar.selectionForeground", Color.black);
        UIManager.put("ProgressBar.selectionBackground", Color.black);
        
        UIManager.put("OptionPane.background", Color.decode("#85929E"));
        UIManager.put("Panel.background", Color.decode("#85929E"));
        
        UIManager.put("Button.background", Color.decode("#5D6D7E"));
        UIManager.put("Button.border", BorderFactory.createCompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(5, 17, 5, 17)));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
        
        setUIFont (new javax.swing.plaf.FontUIResource("Comic Sans MS",Font.PLAIN,12));
        try {
			TelaInicio inicio = new TelaInicio();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void setUIFont (javax.swing.plaf.FontUIResource f){
    java.util.Enumeration keys = UIManager.getDefaults().keys();
    while (keys.hasMoreElements()) {
      Object key = keys.nextElement();
      Object value = UIManager.get (key);
      if (value instanceof javax.swing.plaf.FontUIResource)
        UIManager.put (key, f);
      }
    } 
}
