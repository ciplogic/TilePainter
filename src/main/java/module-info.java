module FxStart {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.swing;
    requires it.unimi.dsi.fastutil;

    exports hellofx;
    exports hellofx.common;
    exports hellofx.dialogs.controllers;

    opens hellofx;
    opens hellofx.dialogs.controllers;

}
