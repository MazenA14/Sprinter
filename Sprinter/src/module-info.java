module Sprinter {
    requires javafx.swt;
    requires javafx.media;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.desktop;
    requires java.xml.crypto;
    requires jdk.compiler;
    opens Gui.View;
    opens Gui.Controller;
    opens Backend;
}