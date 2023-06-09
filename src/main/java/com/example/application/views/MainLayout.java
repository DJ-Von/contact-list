package com.example.application.views;

import com.example.application.security.SecurityService;
import com.example.application.views.list.ContactView;
import com.example.application.views.list.StatusView;
import com.example.application.views.list.CompanyView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {
    private final SecurityService securityService;

    public MainLayout(SecurityService securityService){
        this.securityService = securityService;
        CreateHeader();
        CreateDrawer();
    }
    private void CreateHeader() {
        H1 logo = new H1("Contact List");
        logo.addClassNames("text-l", "m-m");

        Button logOut = new Button("Log Out", e -> securityService.logout());
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logOut);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidthFull();
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);
    }
    private void CreateDrawer() {
        RouterLink listView = new RouterLink("List", ContactView.class);
        listView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink statusView = new RouterLink("Statuses", StatusView.class);
        statusView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink companyView = new RouterLink("Companies", CompanyView.class);
        companyView.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
                listView,
                statusView,
                companyView,
                new RouterLink("Dashboard", DashBoardView.class)
        ));
    }
}
