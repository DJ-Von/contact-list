package com.example.application.views.list;

import com.example.application.data.entity.Status;
import com.example.application.data.service.CrmService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PageTitle("Contacts")
@Route(value = "statuses", layout = MainLayout.class)
@PermitAll
public class StatusView extends VerticalLayout {
    Grid<Status> grid=new Grid<>(Status.class);
    TextField filterText = new TextField();
    StatusForm form;
    private CrmService service;
    public StatusView(CrmService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();

        configureGrid();
        configureForm();
        add(
                getToolbar(),
                getContent()
        );

        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setStatus(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(service.findAllStatuses(filterText.getValue()));
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add status");
        addContactButton.addClickListener(e -> addStatus());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addStatus() {
        grid.asSingleSelect().clear();
        editStatus(new Status());
    }

    private void configureGrid() {
        grid.addClassName("status-grid");
        grid.setSizeFull();
        grid.setColumns("name");
        grid.getColumns().forEach(col->col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editStatus(e.getValue()));
    }

    private void editStatus(Status status) {
        if(status == null){
            closeEditor();
        } else {
            form.setStatus(status);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void configureForm() {
        form = new StatusForm();
        form.setWidth("25em");

        form.addSaveListener(this::saveStatus);
        form.addDeleteListener(this::deleteStatus);
        form.addCloseListener(e -> closeEditor());
    }

    private void saveStatus(StatusForm.SaveEvent event){
        service.saveStatus(event.getStatus());
        updateList();
        closeEditor();
    }

    private void deleteStatus(StatusForm.DeleteEvent event){
        service.deleteStatus(event.getStatus());
        updateList();
        closeEditor();
    }
}
