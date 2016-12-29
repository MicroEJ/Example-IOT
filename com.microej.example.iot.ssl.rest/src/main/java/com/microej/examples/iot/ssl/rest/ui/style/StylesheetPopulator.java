package ej.examples.iot.ssl.rest.ui.style;

import ej.examples.iot.ssl.rest.ui.WrapComposite;
import ej.examples.iot.ssl.rest.ui.out.Line;
import ej.microui.display.Colors;
import ej.microui.display.GraphicsContext;
import ej.navigation.desktop.NavigationDesktop;
import ej.style.Selector;
import ej.style.State;
import ej.style.Stylesheet;
import ej.style.background.NoBackground;
import ej.style.background.PlainBackground;
import ej.style.background.SimpleRoundedPlainBackground;
import ej.style.font.FontProfile;
import ej.style.font.FontProfile.FontSize;
import ej.style.outline.ComplexOutline;
import ej.style.outline.Outline;
import ej.style.outline.SimpleOutline;
import ej.style.selector.ClassSelector;
import ej.style.selector.StateSelector;
import ej.style.selector.TypeSelector;
import ej.style.selector.combinator.AndCombinator;
import ej.style.text.ComplexTextManager;
import ej.style.util.EditableStyle;
import ej.style.util.StyleHelper;
import ej.widget.basic.Label;
import ej.widget.composed.Button;

public class StylesheetPopulator {

	// Prevents initialization.
	private StylesheetPopulator() {
	}

	public static void initialize(NavigationDesktop desktop) {
		Stylesheet stylesheet = StyleHelper.getStylesheet();
		// Sets the default style.
		EditableStyle defaultStyle = new EditableStyle();
		defaultStyle.setForegroundColor(Colors.WHITE);
		FontProfile defaultFontProfile = new FontProfile();
		defaultFontProfile.setFamily(FontFamilies.SOURCE);
		defaultFontProfile.setSize(FontSize.MEDIUM);
		defaultStyle.setFontProfile(defaultFontProfile);
		defaultStyle.setBackground(NoBackground.NO_BACKGROUND);
		defaultStyle.setAlignment(GraphicsContext.HCENTER
				| GraphicsContext.VCENTER);
		stylesheet.setDefaultStyle(defaultStyle);

		// Default margin not added in the default style because it also applies for the composites.
		SimpleOutline defaultMargin = new SimpleOutline(6);

		// Sets the label style.
		EditableStyle labelStyle = new EditableStyle();
		labelStyle.setPadding(defaultMargin);
		labelStyle.setBackground(NoBackground.NO_BACKGROUND);
		// labelStyle.setForegroundColor(MicroEJColors.concreteBlack50);
		stylesheet.addRule(new TypeSelector(Label.class), labelStyle);

		// Sets picto style.
		EditableStyle pictoStyle = new EditableStyle(labelStyle);
		FontProfile pictoFontProfile = new FontProfile();
		pictoFontProfile.setFamily(FontFamilies.PICTO);
		pictoFontProfile.setSize(FontSize.LARGE);
		pictoStyle.setFontProfile(pictoFontProfile);
		stylesheet.addRule(new ClassSelector(ClassSelectors.PICTO), pictoStyle);

		// Sets title style.
		EditableStyle titleStyle = new EditableStyle();
		titleStyle.setPadding(defaultMargin);
		titleStyle.setBackground(new PlainBackground());
		titleStyle.setBackgroundColor(Colors.WHITE);
		titleStyle.setForegroundColor(MicroEJColors.concreteBlack75);
		stylesheet.addRule(new ClassSelector(ClassSelectors.TITLE), titleStyle);

		// Sets line style
		EditableStyle lineStyle = new EditableStyle();
		Outline lineMargin = new ComplexOutline(0, 0, 0, 6);
		lineStyle.setMargin(lineMargin);
		lineStyle.setTextManager(new ComplexTextManager());
		FontProfile lineFontProfile = new FontProfile();
		lineFontProfile.setFamily(FontFamilies.SOURCE);
		lineFontProfile.setSize(FontSize.SMALL);
		lineStyle.setFontProfile(lineFontProfile);
		lineStyle.setAlignment(GraphicsContext.LEFT | GraphicsContext.TOP);
		lineStyle.setForegroundColor(Colors.WHITE);
		stylesheet.addRule(new TypeSelector(Line.class), lineStyle);

		// Sets button style
		Selector buttonSelector = new TypeSelector(Button.class);
		EditableStyle buttonStyle = new EditableStyle();
		Outline buttonMargin = new ComplexOutline(6, 10, 6, 10);
		buttonStyle.setMargin(buttonMargin);
		buttonStyle.setPadding(defaultMargin);
		buttonStyle.setBackground(new SimpleRoundedPlainBackground(15));
		buttonStyle.setBackgroundColor(MicroEJColors.coral);
		buttonStyle.setForegroundColor(Colors.WHITE);
		stylesheet.addRule(buttonSelector, buttonStyle);

		// Sets active button style.
		EditableStyle activeButtonStyle = new EditableStyle();
		activeButtonStyle.setBackgroundColor(MicroEJColors.pomegranate);
		stylesheet.addRule(new AndCombinator(buttonSelector, new StateSelector(State.Active)),
				activeButtonStyle);

		// Sets title text style
		EditableStyle titleTextStyle = new EditableStyle();
		titleTextStyle.setBackground(NoBackground.NO_BACKGROUND);
		stylesheet.addRule(new TypeSelector(WrapComposite.class), titleTextStyle);

		// Sets title style.
		EditableStyle greyBGroundStyle = new EditableStyle();
		greyBGroundStyle.setBackground(new PlainBackground());
		greyBGroundStyle.setBackgroundColor(MicroEJColors.concreteBlack50);
		stylesheet.addRule(new ClassSelector(ClassSelectors.GREY_BG), greyBGroundStyle);
	}

}
