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
        VBox mainContainer = new VBox(20);
        mainContainer.setPadding(new Insets(20));
        mainContainer.setAlignment(Pos.CENTER);

        Label lblTitulo = new Label("Formulário de Cadastro");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        HBox contentContainer = new HBox(20);
        contentContainer.setAlignment(Pos.CENTER);

        VBox imageContainer = new VBox(10);
        imageContainer.setAlignment(Pos.CENTER);
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);

        try {
            imageView.setImage(new Image(getClass().getResourceAsStream("/placeholder.png")));
        } catch (Exception e) {
            Rectangle placeholder = new Rectangle(200, 200);
            placeholder.setStyle("-fx-fill: lightgray; -fx-stroke: gray; -fx-stroke-width: 1;");
            imageContainer.getChildren().add(placeholder);
        }

        Button btnUpload = new Button("Upload Imagem");
        btnUpload.setOnAction(e -> selecionarImagem());
        imageContainer.getChildren().addAll(imageView, btnUpload);

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setAlignment(Pos.TOP_LEFT);

        form.add(createLabeledField("Nome:", txtNome), 0, 0);

        form.add(createLabeledField("Cor da Pelagem:", txtCorPelagem), 0, 1);

        form.add(createLabeledField("Local:", txtLocal), 0, 2);

        form.add(new Label("Sexo:"), 0, 3);
        HBox sexoContainer = new HBox(10, rbtnMacho, rbtnFemea);
        sexoContainer.setAlignment(Pos.CENTER_LEFT);
        form.add(sexoContainer, 1, 3);

        form.add(chkAdotado, 1, 4);
        form.add(chkCastrado, 1, 5);

        form.add(createLabeledField("Descrição:", txtDescricao), 0, 6);
        txtDescricao.setPrefRowCount(4);

        Button btnCadastrar = new Button("Cadastrar");
        btnCadastrar.setStyle("-fx-background-color: #ff9999;");
        btnCadastrar.setOnAction(e -> cadastrarGato());

        HBox buttonBox = new HBox(btnCadastrar);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        form.add(buttonBox, 1, 7);

        contentContainer.getChildren().addAll(imageContainer, form);

        mainContainer.getChildren().addAll(lblTitulo, contentContainer);
        mainContainer.setStyle("-fx-background-color: #ffcccc;");

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

            boolean adotado = chkAdotado.isSelected();
            String sexo = rbtnMacho.isSelected() ? "Macho" : "Fêmea";
            boolean castrado = chkCastrado.isSelected();

            controller.adicionarGato(nome, corPelagem, sexo, adotado, castrado, local, descricao, caminhoImagem);

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
