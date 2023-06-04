package com.example.application.views.list;

import com.example.application.data.entity.Status;
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

public class StatusForm extends FormLayout {
    Binder<Status> binder = new BeanValidationBinder<>(Status.class);
    TextField name = new TextField("Name");
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");
    private Status status;

    public StatusForm() {
        addClassName("status-form");
        binder.bindInstanceFields(this);

        add(name, createButtonLayout());
    }

    public void setStatus(Status status){
        this.status = status;
        binder.readBean(status);
    }

    private Component createButtonLayout(){
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new StatusForm.DeleteEvent(this, status)));
        cancel.addClickListener(event -> fireEvent(new StatusForm.CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);
        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try{
            binder.writeBean(status);
            fireEvent(new StatusForm.SaveEvent(this, status));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class StatusFormEvent extends ComponentEvent<StatusForm> {
        private Status status;

        protected StatusFormEvent(StatusForm source, Status status) {
            super(source, false);
            this.status = status;
        }

        public Status getStatus() {
            return status;
        }
    }

    public static class SaveEvent extends StatusForm.StatusFormEvent {
        SaveEvent(StatusForm source, Status status) {
            super(source, status);
        }
    }

    public static class DeleteEvent extends StatusForm.StatusFormEvent {
        DeleteEvent(StatusForm source, Status status) {
            super(source, status);
        }

    }

    public static class CloseEvent extends StatusForm.StatusFormEvent {
        CloseEvent(StatusForm source) {
            super(source, null);
        }
    }

    public Registration addDeleteListener(ComponentEventListener<StatusForm.DeleteEvent> listener) {
        return addListener(StatusForm.DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<StatusForm.SaveEvent> listener) {
        return addListener(StatusForm.SaveEvent.class, listener);
    }

    public Registration addCloseListener(ComponentEventListener<StatusForm.CloseEvent> listener) {
        return addListener(StatusForm.CloseEvent.class, listener);
    }
}
