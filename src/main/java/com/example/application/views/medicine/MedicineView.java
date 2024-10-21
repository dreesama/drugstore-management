package com.example.application.views.medicine;

import com.example.application.data.Medicine;
import com.example.application.services.MedicineService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@PageTitle("Medicine")
@Route(value = "Medicine/:medicineID?/:action?(edit)", layout = MainLayout.class)
public class MedicineView extends Div implements BeforeEnterObserver {

    private final String MEDICINE_ID = "medicineID";
    private final String MEDICINE_EDIT_ROUTE_TEMPLATE = "Medicine/%s/edit";

    private final Grid<Medicine> grid = new Grid<>(Medicine.class, false);

    private TextField brandName;
    private TextField genericName;
    private TextField dosageSize;
    private TextField dosageForm;
    private TextField medicineDescription;
    private TextField medicineImage;
    private TextField medicinePrice;

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    private final BeanValidationBinder<Medicine> binder;

    private Medicine medicine;

    private final MedicineService medicineService;

    public MedicineView(MedicineService medicineService) {
        this.medicineService = medicineService;
        addClassNames("medicine-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("brandName").setAutoWidth(true);
        grid.addColumn("genericName").setAutoWidth(true);
        grid.addColumn("dosageSize").setAutoWidth(true);
        grid.addColumn("dosageForm").setAutoWidth(true);
        grid.addColumn("medicineDescription").setAutoWidth(true);
        grid.addColumn("medicineImage").setAutoWidth(true);
        grid.addColumn("medicinePrice").setAutoWidth(true);
        grid.setItems(query -> medicineService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(MEDICINE_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(MedicineView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Medicine.class);

        // Bind fields. This is where you'd define e.g. validation rules
        binder.forField(medicinePrice).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("medicinePrice");

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.medicine == null) {
                    this.medicine = new Medicine();
                }
                binder.writeBean(this.medicine);
                medicineService.update(this.medicine);
                clearForm();
                refreshGrid();
                Notification.show("Data updated");
                UI.getCurrent().navigate(MedicineView.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (ValidationException validationException) {
                Notification.show("Failed to update the data. Check again that all values are valid");
            }
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> medicineId = event.getRouteParameters().get(MEDICINE_ID).map(Long::parseLong);
        if (medicineId.isPresent()) {
            Optional<Medicine> medicineFromBackend = medicineService.get(medicineId.get());
            if (medicineFromBackend.isPresent()) {
                populateForm(medicineFromBackend.get());
            } else {
                Notification.show(String.format("The requested medicine was not found, ID = %s", medicineId.get()),
                        3000, Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(MedicineView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        brandName = new TextField("Brand Name");
        genericName = new TextField("Generic Name");
        dosageSize = new TextField("Dosage Size");
        dosageForm = new TextField("Dosage Form");
        medicineDescription = new TextField("Medicine Description");
        medicineImage = new TextField("Medicine Image");
        medicinePrice = new TextField("Medicine Price");
        formLayout.add(brandName, genericName, dosageSize, dosageForm, medicineDescription, medicineImage,
                medicinePrice);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Medicine value) {
        this.medicine = value;
        binder.readBean(this.medicine);

    }
}
