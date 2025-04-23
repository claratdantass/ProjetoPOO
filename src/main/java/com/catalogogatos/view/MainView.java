package com.catalogogatos.view;

import com.catalogogatos.controller.MainController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;

public class MainView {
    private final MainController controller;
    private final BorderPane root;
    private final CadastroView cadastroView;
    private final CatalogoView catalogoView;
    private final StackPane contentPane;

    public MainView(MainController controller) {
        this.controller = controller;
        this.root = new BorderPane();
        this.cadastroView = new CadastroView(controller);
        this.catalogoView = new CatalogoView(controller);
        this.contentPane = new StackPane();

        setupUI();
    }

    private void setupUI() {
        BorderPane initialScreen = new BorderPane();
        
        Text title = new Text("Catálogo de Gatos UFPB");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        initialScreen.setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);

        Button btnCadastro = new Button("Cadastrar Gato");
        Button btnCatalogo = new Button("Ver Catálogo");
        
        btnCadastro.setOnAction(e -> mostrarCadastro());
        btnCatalogo.setOnAction(e -> mostrarCatalogo());

        VBox buttonsPane = new VBox(); 
        buttonsPane.setSpacing(10); 
        buttonsPane.setAlignment(Pos.CENTER);
        buttonsPane.getChildren().addAll(btnCadastro, btnCatalogo);

        initialScreen.setCenter(buttonsPane);

        root.setCenter(initialScreen);
    }

    private void mostrarCadastro() {
        BorderPane cadastroScreen = new BorderPane();
        Button btnVoltarCadastro = new Button("Voltar");
        btnVoltarCadastro.setOnAction(e -> voltarTelaInicial());

        cadastroScreen.setTop(btnVoltarCadastro);
        cadastroScreen.setCenter(cadastroView.getRoot());

        root.setCenter(cadastroScreen);
    }

    private void mostrarCatalogo() {
        catalogoView.atualizarCatalogo();
        BorderPane catalogoScreen = new BorderPane();
        Button btnVoltarCatalogo = new Button("Voltar");
        btnVoltarCatalogo.setOnAction(e -> voltarTelaInicial());

        catalogoScreen.setTop(btnVoltarCatalogo);
        catalogoScreen.setCenter(catalogoView.getRoot());

        root.setCenter(catalogoScreen);
    }

    private void voltarTelaInicial() {
        setupUI(); 
    }

    public BorderPane getRoot() {
        return root;
    }
}
