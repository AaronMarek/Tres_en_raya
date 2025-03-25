package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class Controller implements Initializable {
    
    @FXML
    private GridPane myGridPane;
    
    @FXML
    private Label myLabel;
    
    @FXML
    private Button myButton1, myButton2, myButton3, myButton4, myButton5, myButton6, myButton7, myButton8, myButton9, myButtonReset;

    private int turnoJugador;
    
    ArrayList<Button> botones;
    
    @Override
    public void initialize(URL arg0, ResourceBundle resourceBundle) {
        turnoJugador = 0; // 0 para X, 1 para O
        botones = new ArrayList<>(Arrays.asList(myButton1, myButton2, myButton3, myButton4, myButton5, 
                                               myButton6, myButton7, myButton8, myButton9));
        
        botones.forEach(button -> setupButton(button));
        myButtonReset.setOnAction(e -> restearJuego(null));
    }

    private void setupButton(Button button) {
        button.setOnAction(e -> {
            asignarSimbolos(button);
            comprobarGanador(button);
        });
        
        // Configurar el estilo y tamaño del texto
        button.setFont(new Font("Arial", 40));
    }
    
    private void restearJuego(Button button) {
        // Limpiar texto de todos los botones y habilitarlos
        botones.forEach(btn -> {
            btn.setText("");
            btn.setDisable(false);
        });
        
        // Reiniciar turno y mensaje
        turnoJugador = 0;
        myLabel.setText("Jugador X empieze:");
    }
    
    public void comprobarGanador(Button button) {
        // Comprobar si hay un ganador
        boolean hayGanador = false;
        
        // Comprobar filas
        for (int i = 0; i < 3; i++) {
            if (botones.get(i*3).getText().equals(botones.get(i*3+1).getText()) && 
                botones.get(i*3+1).getText().equals(botones.get(i*3+2).getText()) && 
                !botones.get(i*3).getText().isEmpty()) {
                hayGanador = true;
                break;
            }
        }
        
        // Comprobar columnas
        for (int i = 0; i < 3; i++) {
            if (botones.get(i).getText().equals(botones.get(i+3).getText()) && 
                botones.get(i+3).getText().equals(botones.get(i+6).getText()) && 
                !botones.get(i).getText().isEmpty()) {
                hayGanador = true;
                break;
            }
        }
        
        // Comprobar diagonal principal
        if (botones.get(0).getText().equals(botones.get(4).getText()) && 
            botones.get(4).getText().equals(botones.get(8).getText()) && 
            !botones.get(0).getText().isEmpty()) {
            hayGanador = true;
        }
        
        // Comprobar diagonal inversa
        if (botones.get(2).getText().equals(botones.get(4).getText()) && 
            botones.get(4).getText().equals(botones.get(6).getText()) && 
            !botones.get(2).getText().isEmpty()) {
            hayGanador = true;
        }
        
        // Si hay un ganador, mostrar mensaje y deshabilitar botones
        if (hayGanador) {
            String ganador = turnoJugador % 2 == 0 ? "O" : "X";
            myLabel.setText("¡El Jugador " + ganador + " ha ganado!");
            botones.forEach(btn -> btn.setDisable(true));
            return;
        }
        
        // Comprobar empate (todos los botones llenos)
        boolean empate = true;
        for (Button btn : botones) {
            if (btn.getText().isEmpty()) {
                empate = false;
                break;
            }
        }
        
        if (empate) {
            myLabel.setText("¡Empate!");
        }
    }
    
    public void asignarSimbolos(Button button) {
        // Verificar si el botón ya tiene un símbolo
        if (!button.getText().isEmpty())
            return;
        
        // Asignar X u O según el turno
        if (turnoJugador % 2 == 0) {
            button.setText("X");
            myLabel.setText("Turno del Jugador O");
        } else {
            button.setText("O");
            myLabel.setText("Turno del Jugador X");
        }
        
        // Incrementar el turno
        turnoJugador++;
    }
}
