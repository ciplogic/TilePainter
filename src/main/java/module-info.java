module JHeroes2X {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.swing;
    requires it.unimi.dsi.fastutil;

    exports hellofx;
    exports hellofx.common;
    exports hellofx.dialogs.controllers;

    opens hellofx;
    opens hellofx.dialogs.controllers;

}
