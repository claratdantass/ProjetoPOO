package com.catalogogatos.view;

import com.catalogogatos.controller.MainController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import com.catalogogatos.model.Gato;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class CadastroView {
    private final MainController controller;
    private final BorderPane root;
    private final TextField txtNome;
    private final TextField txtCorPelagem;
    private final TextField txtLocal;
    private final TextArea txtDescricao;
    private final ImageView imageView;
    private File selectedFile;
    private final CheckBox chkAdotado;
    private final ToggleGroup sexoGroup;
    private final RadioButton rbtnMacho;
    private final RadioButton rbtnFemea;
    private final CheckBox chkCastrado;

    public CadastroView(MainController controller) {
        this.controller = controller;
        this.root = new BorderPane();
        this.txtNome = new TextField();
        this.txtCorPelagem = new TextField();
        this.txtLocal = new TextField();
        this.txtDescricao = new TextArea();
        this.imageView = new ImageView();
        this.chkAdotado = new CheckBox("Adotado");
        this.sexoGroup = new ToggleGroup();
        this.rbtnMacho = new RadioButton("Macho");
        this.rbtnFemea = new RadioButton("Fêmea");
        this.chkCastrado = new CheckBox("Castrado");

        rbtnMacho.setToggleGroup(sexoGroup);
        rbtnFemea.setToggleGroup(sexoGroup);

        setupUI();
    }

    private void setupUI() {
        VBox mainContainer = new VBox(30);
        mainContainer.setPadding(new Insets(30, 40, 30, 40));
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setStyle("-fx-background-color: #eaf6fb; -fx-border-color: #2980b9; -fx-border-width: 2px; -fx-border-radius: 12px;");

        Label lblTitulo = new Label("Formulário de Cadastro de Gato");
        lblTitulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2980b9; -fx-padding: 0 0 10 0;");

        GridPane form = new GridPane();
        form.setHgap(20);
        form.setVgap(16);
        form.setAlignment(Pos.CENTER);
        form.setStyle("-fx-background-color: #eaf6fb; -fx-padding: 24px 32px 24px 32px; -fx-border-radius: 8px; -fx-effect: dropshadow(gaussian,rgb(179, 32, 159), 6, 0, 0, 2);");

        form.add(new Label("Nome:"), 0, 0);
        form.add(new Label("Cor da Pelagem:"), 0, 1);
        form.add(new Label("Local:"), 0, 2);
        form.add(new Label("Sexo:"), 0, 3);
        form.add(new Label("Adotado:"), 0, 4);
        form.add(new Label("Castrado:"), 0, 5);
        form.add(new Label("Descrição:"), 0, 6);

        form.add(txtNome, 1, 0);
        form.add(txtCorPelagem, 1, 1);
        form.add(txtLocal, 1, 2);
        HBox sexoContainer = new HBox(16, rbtnMacho, rbtnFemea);
        sexoContainer.setAlignment(Pos.CENTER_LEFT);
        form.add(sexoContainer, 1, 3);
        form.add(chkAdotado, 1, 4);
        form.add(chkCastrado, 1, 5);
        txtDescricao.setPrefRowCount(3);
        txtDescricao.setPrefWidth(230);
        form.add(txtDescricao, 1, 6);

        VBox imageBox = new VBox(12);
        imageBox.setAlignment(Pos.CENTER);
        imageView.setFitWidth(120);
        imageView.setFitHeight(120);
        imageView.setPreserveRatio(false);
        imageBox.setStyle("-fx-background-color: #eaf6fb; " +
                "-fx-padding: 10px; " +
                "-fx-border-radius: 10px; " +
                "-fx-border-color: rgba(242,148,230,0.45); " +
                "-fx-border-width: 2px; " +
                "-fx-effect: dropshadow(gaussian, rgba(242,148,230,0.45), 8, 0, 0, 2);");
        try {
            imageView.setImage(new Image(getClass().getResourceAsStream("/placeholder.png")));
        } catch (Exception e) {
            Rectangle placeholder = new Rectangle(120, 120);
            placeholder.setStyle("-fx-fill: #eaf6fb; -fx-stroke: rgba(242,148,230,0.45); -fx-stroke-width: 2;");
            imageBox.getChildren().add(placeholder);
        }
        Button btnUpload = new Button("Selecionar Imagem");
        btnUpload.setStyle("-fx-background-color: rgb(179, 32, 159); -fx-font-weight: bold; -fx-text-fill: #fff; -fx-padding: 7 20; -fx-background-radius: 8px;");
        btnUpload.setOnAction(e -> selecionarImagem());
        imageBox.getChildren().addAll(imageView, btnUpload);

        Button btnCadastrar = new Button("Cadastrar");
        btnCadastrar.setStyle("-fx-background-color:rgb(242, 148, 230; -fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold; -fx-padding: 10 32; -fx-background-radius: 8px;");
        btnCadastrar.setOnAction(e -> cadastrarGato());
        HBox buttonBox = new HBox(btnCadastrar);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setPadding(new Insets(12,0,0,0));
        form.add(buttonBox, 1, 7);

        HBox contentBox = new HBox(36);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.getChildren().addAll(imageBox, form);

        mainContainer.getChildren().addAll(lblTitulo, contentBox);
        root.setCenter(mainContainer);
    }

    private VBox createLabeledField(String labelText, Control inputField) {
        VBox labeledField = new VBox(5);
        Label label = new Label(labelText);
        inputField.setPrefWidth(250); 
        labeledField.getChildren().addAll(label, inputField);
        return labeledField;
    }

    private void selecionarImagem() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar Imagem");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        selectedFile = fileChooser.showOpenDialog(root.getScene().getWindow());
        if (selectedFile != null) {
            try {
                Image image = new Image(selectedFile.toURI().toString());
                imageView.setImage(image);
            } catch (Exception e) {
                exibirAlerta("Erro ao carregar imagem", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    private void cadastrarGato() {
        String nome = txtNome.getText().trim();
        String corPelagem = txtCorPelagem.getText().trim();
        String local = txtLocal.getText().trim();
        String descricao = txtDescricao.getText().trim();

        if (nome.isEmpty() || corPelagem.isEmpty() || local.isEmpty()) {
            exibirAlerta("Campos obrigatórios", "Nome, Cor da Pelagem e Local são campos obrigatórios.", Alert.AlertType.WARNING);
            return;
        }

        try {
            String caminhoImagem = "sem_imagem.png";
            if (selectedFile != null) {
                Path diretorioImagens = Paths.get("imagens");
                if (!Files.exists(diretorioImagens)) {
                    Files.createDirectories(diretorioImagens);
                }

                String extensao = selectedFile.getName().substring(selectedFile.getName().lastIndexOf("."));
                String nomeArquivo = "gato_" + System.currentTimeMillis() + extensao;
                Path destino = diretorioImagens.resolve(nomeArquivo);

                Files.copy(selectedFile.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);
                caminhoImagem = destino.toString();
            }

            String sexo = rbtnMacho.isSelected() ? "Macho" : "Fêmea";
            boolean castrado = chkCastrado.isSelected();

            Gato gato = new Gato();
            gato.setNome(nome);
            gato.setCorPelagem(corPelagem);
            gato.setSexo(sexo);
            gato.setCastrado(castrado);
            gato.setLocal(local);
            gato.setDescricao(descricao);
            gato.setCaminhoImagem(caminhoImagem);

            if (chkAdotado.isSelected()) {
                gato.adotar();
            }

            controller.adicionarGato(gato);

            txtNome.clear();
            txtCorPelagem.clear();
            txtLocal.clear();
            txtDescricao.clear();
            imageView.setImage(new Image(getClass().getResourceAsStream("/placeholder.png")));
            selectedFile = null;

            exibirAlerta("Sucesso", "Gato cadastrado com sucesso!", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            exibirAlerta("Erro ao cadastrar", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void exibirAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public BorderPane getRoot() {
        return root;
    }
}
