package com.example.application.views.list;

import com.example.application.data.entity.Company;
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

@PageTitle("Companies")
@Route(value = "companies", layout = MainLayout.class)
@PermitAll
public class CompanyView extends VerticalLayout {
    Grid<Company> grid=new Grid<>(Company.class);
    TextField filterText = new TextField();
    CompanyForm form;
    private CrmService service;

    public CompanyView(CrmService service) {
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
        form.setCompany(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(service.findAllCompanies(filterText.getValue()));
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

        Button addContactButton = new Button("Add company");
        addContactButton.addClickListener(e -> addCompany());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addCompany() {
        grid.asSingleSelect().clear();
        editCompany(new Company());
    }

    private void configureGrid() {
        grid.addClassName("company-grid");
        grid.setSizeFull();
        grid.setColumns("name");
        grid.getColumns().forEach(col->col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editCompany(e.getValue()));
    }

    private void editCompany(Company company) {
        if(company == null){
            closeEditor();
        } else {
            form.setCompany(company);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void configureForm() {
        form = new CompanyForm();
        form.setWidth("25em");

        form.addSaveListener(this::saveCompany);
        form.addDeleteListener(this::deleteCompany);
        form.addCloseListener(e -> closeEditor());
    }

    private void saveCompany(CompanyForm.SaveEvent event){
        service.saveCompany(event.getCompany());
        updateList();
        closeEditor();
    }

    private void deleteCompany(CompanyForm.DeleteEvent event){
        service.deleteCompany(event.getCompany());
        updateList();
        closeEditor();
    }
}
