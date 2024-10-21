package com.example.application.views.pos;

import com.example.application.components.pricefield.PriceField;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@PageTitle("POS ")
@Route(value = "my-view", layout = MainLayout.class)
public class POSView extends Composite<VerticalLayout> {

    public POSView() {
        HorizontalLayout layoutRow = new HorizontalLayout();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        H4 h4 = new H4();
        MultiSelectComboBox multiSelectComboBox = new MultiSelectComboBox();
        PriceField price = new PriceField();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        HorizontalLayout layoutRow3 = new HorizontalLayout();
        NumberField numberField = new NumberField();
        HorizontalLayout layoutRow4 = new HorizontalLayout();
        Button buttonPrimary = new Button();
        VerticalLayout layoutColumn3 = new VerticalLayout();
        H4 h42 = new H4();
        MessageList messageList = new MessageList();
        VerticalLayout layoutColumn4 = new VerticalLayout();
        H4 h43 = new H4();
        MultiSelectListBox textItems = new MultiSelectListBox();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setMaxHeight("250px");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.START);
        layoutRow.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        layoutRow.setAlignItems(Alignment.START);
        layoutRow.setJustifyContentMode(JustifyContentMode.CENTER);
        layoutColumn2.setHeightFull();
        layoutRow.setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.addClassName(Gap.XSMALL);
        layoutColumn2.getStyle().set("flex-grow", "1");
        layoutColumn2.setHeight("min-content");
        layoutColumn2.setJustifyContentMode(JustifyContentMode.START);
        layoutColumn2.setAlignItems(Alignment.START);
        h4.setText("Point of Sale");
        h4.setWidth("max-content");
        multiSelectComboBox.setLabel("Select Medicine");
        multiSelectComboBox.setWidth("310px");
        setMultiSelectComboBoxSampleData(multiSelectComboBox);
        price.setLabel("Price");
        price.setWidth("min-content");
        layoutRow2.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow2);
        layoutRow2.setWidth("100%");
        layoutRow2.getStyle().set("flex-grow", "1");
        layoutRow2.setAlignItems(Alignment.END);
        layoutRow2.setJustifyContentMode(JustifyContentMode.START);
        layoutRow3.setHeightFull();
        layoutRow2.setFlexGrow(1.0, layoutRow3);
        layoutRow3.addClassName(Gap.MEDIUM);
        layoutRow3.setWidth("min-content");
        layoutRow3.getStyle().set("flex-grow", "1");
        numberField.setLabel("Quantity");
        numberField.setWidth("min-content");
        numberField.setMaxWidth("100px");
        layoutRow4.setHeightFull();
        layoutRow2.setFlexGrow(1.0, layoutRow4);
        layoutRow4.addClassName(Gap.MEDIUM);
        layoutRow4.getStyle().set("flex-grow", "1");
        layoutRow4.setMaxWidth("120px");
        layoutRow4.setHeight("57px");
        layoutRow4.setAlignItems(Alignment.END);
        layoutRow4.setJustifyContentMode(JustifyContentMode.CENTER);
        buttonPrimary.setText("Update");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        layoutColumn3.setHeightFull();
        layoutRow.setFlexGrow(1.0, layoutColumn3);
        layoutColumn3.setWidth("100%");
        layoutColumn3.setHeight("100%");
        h42.setText("Live Chat");
        h42.setWidth("max-content");
        messageList.setWidth("100%");
        setMessageListSampleData(messageList);
        layoutColumn4.setWidthFull();
        getContent().setFlexGrow(1.0, layoutColumn4);
        layoutColumn4.setWidth("342px");
        layoutColumn4.getStyle().set("flex-grow", "1");
        h43.setText("Recent Updates");
        h43.setWidth("max-content");
        textItems.setWidth("100%");
        textItems.setMaxWidth("500px");
        textItems.setMaxHeight("100%");
        setMultiSelectListBoxSampleData(textItems);
        getContent().add(layoutRow);
        layoutRow.add(layoutColumn2);
        layoutColumn2.add(h4);
        layoutColumn2.add(multiSelectComboBox);
        layoutColumn2.add(price);
        layoutColumn2.add(layoutRow2);
        layoutRow2.add(layoutRow3);
        layoutRow3.add(numberField);
        layoutRow2.add(layoutRow4);
        layoutRow4.add(buttonPrimary);
        layoutRow.add(layoutColumn3);
        layoutColumn3.add(h42);
        layoutColumn3.add(messageList);
        getContent().add(layoutColumn4);
        layoutColumn4.add(h43);
        layoutColumn4.add(textItems);
    }

    record SampleItem(String value, String label, Boolean disabled) {
    }

    private void setMultiSelectComboBoxSampleData(MultiSelectComboBox multiSelectComboBox) {
        List<SampleItem> sampleItems = new ArrayList<>();
        sampleItems.add(new SampleItem("first", "First", null));
        sampleItems.add(new SampleItem("second", "Second", null));
        sampleItems.add(new SampleItem("third", "Third", Boolean.TRUE));
        sampleItems.add(new SampleItem("fourth", "Fourth", null));
        multiSelectComboBox.setItems(sampleItems);
        multiSelectComboBox.setItemLabelGenerator(item -> ((SampleItem) item).label());
    }

    private void setMessageListSampleData(MessageList messageList) {
        MessageListItem message1 = new MessageListItem("Nature does not hurry, yet everything gets accomplished.",
                LocalDateTime.now().minusDays(1).toInstant(ZoneOffset.UTC), "Matt Mambo");
        message1.setUserColorIndex(1);
        MessageListItem message2 = new MessageListItem(
                "Using your talent, hobby or profession in a way that makes you contribute with something good to this world is truly the way to go.",
                LocalDateTime.now().minusMinutes(55).toInstant(ZoneOffset.UTC), "Linsey Listy");
        message2.setUserColorIndex(2);
        messageList.setItems(message1, message2);
    }

    private void setMultiSelectListBoxSampleData(MultiSelectListBox multiSelectListBox) {
        List<SampleItem> sampleItems = new ArrayList<>();
        sampleItems.add(new SampleItem("first", "First", null));
        sampleItems.add(new SampleItem("second", "Second", null));
        sampleItems.add(new SampleItem("third", "Third", Boolean.TRUE));
        sampleItems.add(new SampleItem("fourth", "Fourth", null));
        multiSelectListBox.setItems(sampleItems);
        multiSelectListBox.setItemLabelGenerator(item -> ((SampleItem) item).label());
        multiSelectListBox.setItemEnabledProvider(item -> !Boolean.TRUE.equals(((SampleItem) item).disabled()));
    }
}
