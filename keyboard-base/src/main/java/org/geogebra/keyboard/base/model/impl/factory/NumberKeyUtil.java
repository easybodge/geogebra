package org.geogebra.keyboard.base.model.impl.factory;

import org.geogebra.keyboard.base.Action;
import org.geogebra.keyboard.base.Resource;
import org.geogebra.keyboard.base.model.impl.RowImpl;

import static org.geogebra.keyboard.base.model.impl.factory.Characters.DIVISION;
import static org.geogebra.keyboard.base.model.impl.factory.Characters.MULTIPLICATION;
import static org.geogebra.keyboard.base.model.impl.factory.Util.addConstantCustomButton;
import static org.geogebra.keyboard.base.model.impl.factory.Util.addInputButton;

public class NumberKeyUtil {

    static void addFirstRow(RowImpl row, ButtonFactory buttonFactory) {
        addInputButton(row, buttonFactory, "7");
        addInputButton(row, buttonFactory, "8");
        addInputButton(row, buttonFactory, "9");
        addInputButton(row, buttonFactory, MULTIPLICATION, "*");
        addInputButton(row, buttonFactory, DIVISION, "/");
    }

    static void addSecondRow(RowImpl row, ButtonFactory buttonFactory) {
        addInputButton(row, buttonFactory, "4");
        addInputButton(row, buttonFactory, "5");
        addInputButton(row, buttonFactory, "6");
        addInputButton(row, buttonFactory, "+");
        addInputButton(row, buttonFactory, "-");
    }

    static void addThirdRow(RowImpl row, ButtonFactory buttonFactory) {
        addInputButton(row, buttonFactory, "1");
        addInputButton(row, buttonFactory, "2");
        addInputButton(row, buttonFactory, "3");
        addInputButton(row, buttonFactory, "=");
        addConstantCustomButton(row, buttonFactory, Resource.BACKSPACE_DELETE,
                Action.BACKSPACE_DELETE);
    }

    static void addFourthRow(RowImpl row, ButtonFactory buttonFactory) {
        addInputButton(row, buttonFactory, "0");
        addInputButton(row, buttonFactory, ".");
        addConstantCustomButton(row, buttonFactory, Resource.LEFT_ARROW, Action.LEFT_CURSOR);
        addConstantCustomButton(row, buttonFactory, Resource.RIGHT_ARROW, Action.RIGHT_CURSOR);
        addConstantCustomButton(row, buttonFactory, Resource.RETURN_ENTER, Action.RETURN_ENTER);
    }
}
