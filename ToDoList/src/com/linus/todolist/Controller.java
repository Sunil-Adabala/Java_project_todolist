package com.linus.todolist;

import com.linus.todolist.datamodel.TodoData;
import com.linus.todolist.datamodel.TodoItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Controller {

    private List<TodoItem> todoItems;
    @FXML
    private ListView<TodoItem> todoListView;
    @FXML
    private TextArea itemDetailsTextArea;
    @FXML
    private Label deadLineLabel;
    @FXML
    private BorderPane mainBorderPane;

    public void initialize() {
//        TodoItem item1=new TodoItem("Read Dbms","Read dbms to prep for mid 2 ", LocalDate.of(2019,Month.APRIL,15));
//        TodoItem item2=new TodoItem("Read java ","Read java to prep for mid 2", LocalDate.of(2019,Month.APRIL,16));
//        TodoItem item3=new TodoItem("Read DAA","Read DAA to prep for mid 2", LocalDate.of(2019,Month.APRIL,17));
//        TodoItem item4=new TodoItem("Read SE","Read SE to prep for mid 2", LocalDate.of(2019,Month.APRIL,18));
//        TodoItem item5=new TodoItem("Read CO","Read SE to prep for mid 2", LocalDate.of(2019,Month.APRIL,19));
//
//        todoItems = new ArrayList<TodoItem>();
//        todoItems.add(item1);
//        todoItems.add(item2);
//        todoItems.add(item3);
//        todoItems.add(item4);
//        todoItems.add(item5);



        todoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TodoItem>() {
            @Override
            public void changed(ObservableValue<? extends TodoItem> observableValue, TodoItem todoItem, TodoItem t1) {
                if(t1 != null){
                    TodoItem item = todoListView.getSelectionModel().getSelectedItem();
                    itemDetailsTextArea.setText(item.getDetails());
                    //itemDetailsTextArea.setText(item.getDeadline().toString());
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d, yyyy"); // "d M yy");
                    deadLineLabel.setText(df.format(item.getDeadline()));
                }
            }
        });

        todoListView.getItems().setAll(TodoData.getInstance().getTodoItems());
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        todoListView.getSelectionModel().selectFirst();

    }

    @FXML
    public void showNewItemDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow()); //Assigning parent window by giving an id inorder to gain access here mainWindow pane is added as         //parent to dialog pane
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("ToDoItemDailog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch(IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK); //Creating an ok button
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);//Creating a cancel button

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent()&&result.get()==ButtonType.OK){

        DialogController controller = fxmlLoader.getController();
        controller.processResults();
        System.out.println("Ok button pressed"); //printing ok pressed when OK button is pressed
        todoListView.getItems().setAll(TodoData.getInstance().getTodoItems());
        }
        if(result.isPresent()&&result.get()==ButtonType.CANCEL){
            System.out.println("Cancel button pressed"); //printing cancel... when cancel button is pressed
        }

    }




    @FXML
    public void handleClickListView(){
        TodoItem item = todoListView.getSelectionModel().getSelectedItem();
        itemDetailsTextArea.setText(item.getDetails());
        deadLineLabel.setText(item.getDeadline().toString());

//        StringBuilder sb = new StringBuilder(item.getDetails());
//        sb.append("\n\n\n");
//        sb.append("due : ");
//        sb.append(item.getDeadline());
//        itemDetailsTextArea.setText(sb.toString());

        //itemDetailsTextArea.setText(item.getDeadline().toString());
//        System.out.println("you selected -->"+item);


    }
}






