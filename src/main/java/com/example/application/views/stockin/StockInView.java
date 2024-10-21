package com.example.application.views.stockin;

import com.example.application.data.Stock;
import com.example.application.services.StockService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
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

@PageTitle("Stock In")
@Route(value = "Stock/:stockID?/:action?(edit)", layout = MainLayout.class)
public class StockInView extends Div implements BeforeEnterObserver {

    private final String STOCK_ID = "stockID";
    private final String STOCK_EDIT_ROUTE_TEMPLATE = "Stock/%s/edit";

    private final Grid<Stock> grid = new Grid<>(Stock.class, false);

    private TextField brandName;
    private TextField genericName;
    private TextField medicineQuantity;
    private DatePicker expiration;

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    private final BeanValidationBinder<Stock> binder;

    private Stock stock;

    private final StockService stockService;

    public StockInView(StockService stockService) {
        this.stockService = stockService;
        addClassNames("stock-in-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("brandName").setAutoWidth(true);
        grid.addColumn("genericName").setAutoWidth(true);
        grid.addColumn("medicineQuantity").setAutoWidth(true);
        grid.addColumn("expiration").setAutoWidth(true);
        grid.setItems(query -> stockService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(STOCK_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(StockInView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Stock.class);

        // Bind fields. This is where you'd define e.g. validation rules
        binder.forField(medicineQuantity).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("medicineQuantity");

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.stock == null) {
                    this.stock = new Stock();
                }
                binder.writeBean(this.stock);
                stockService.update(this.stock);
                clearForm();
                refreshGrid();
                Notification.show("Data updated");
                UI.getCurrent().navigate(StockInView.class);
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
        Optional<Long> stockId = event.getRouteParameters().get(STOCK_ID).map(Long::parseLong);
        if (stockId.isPresent()) {
            Optional<Stock> stockFromBackend = stockService.get(stockId.get());
            if (stockFromBackend.isPresent()) {
                populateForm(stockFromBackend.get());
            } else {
                Notification.show(String.format("The requested stock was not found, ID = %s", stockId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(StockInView.class);
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
        medicineQuantity = new TextField("Medicine Quantity");
        expiration = new DatePicker("Expiration");
        formLayout.add(brandName, genericName, medicineQuantity, expiration);

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

    private void populateForm(Stock value) {
        this.stock = value;
        binder.readBean(this.stock);

    }
}
