package com.example.application.views.list;

import com.example.application.data.entity.Company;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class CompanyForm extends FormLayout {
    Binder<Company> binder = new BeanValidationBinder<>(Company.class);
    TextField name = new TextField("Name");
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");
    private Company company;

    public CompanyForm() {
        addClassName("Company-form");
        binder.bindInstanceFields(this);

        add(name, createButtonLayout());
    }

    public void setCompany(Company company){
        this.company = company;
        binder.readBean(company);
    }

    private Component createButtonLayout(){
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new CompanyForm.DeleteEvent(this, company)));
        cancel.addClickListener(event -> fireEvent(new CompanyForm.CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);
        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try{
            binder.writeBean(company);
            fireEvent(new CompanyForm.SaveEvent(this, company));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class CompanyFormEvent extends ComponentEvent<CompanyForm> {
        private Company company;

        protected CompanyFormEvent(CompanyForm source, Company company) {
            super(source, false);
            this.company = company;
        }

        public Company getCompany() {
            return company;
        }
    }

    public static class SaveEvent extends CompanyForm.CompanyFormEvent {
        SaveEvent(CompanyForm source, Company company) {
            super(source, company);
        }
    }

    public static class DeleteEvent extends CompanyForm.CompanyFormEvent {
        DeleteEvent(CompanyForm source, Company company) {
            super(source, company);
        }

    }

    public static class CloseEvent extends CompanyForm.CompanyFormEvent {
        CloseEvent(CompanyForm source) {
            super(source, null);
        }
    }

    public Registration addDeleteListener(ComponentEventListener<CompanyForm.DeleteEvent> listener) {
        return addListener(CompanyForm.DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<CompanyForm.SaveEvent> listener) {
        return addListener(CompanyForm.SaveEvent.class, listener);
    }

    public Registration addCloseListener(ComponentEventListener<CompanyForm.CloseEvent> listener) {
        return addListener(CompanyForm.CloseEvent.class, listener);
    }
}
