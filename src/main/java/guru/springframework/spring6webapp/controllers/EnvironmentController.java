package guru.springframework.spring6webapp.controllers;

import guru.springframework.spring6webapp.services.DataSourceString;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class EnvironmentController {

    private final DataSourceString dataSourceString;

    public EnvironmentController(@Qualifier("environmentService") DataSourceString dataSourceString) {
        this.dataSourceString = dataSourceString;
    }

    public String print() {
        return "We are in environment: " + dataSourceString.printData();
    }

}
