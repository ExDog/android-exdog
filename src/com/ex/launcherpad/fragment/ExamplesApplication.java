package com.ex.launcherpad.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.app.Application;
import ex.widget.gl.GlWorld;


public class ExamplesApplication extends Application {
		
	static enum Category {
		
		// @formatter:off
			GENERAL("General")
		, LIGHTS("Lights")
		, INTERACTIVE("Interactive")
		, UI("UI")
		, OPTIMIZATIONS("Optimizations")
		, PARSERS("Parsers")
		, ANIMATION("Animation")
		, MATERIALS("Materials")
		, ABOUT("About");
		// @formatter:on
   
		private String name;

		Category(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

	}

	public static final Map<Category, ExampleItem[]> ITEMS = new HashMap<Category, ExamplesApplication.ExampleItem[]>();
	public static final ArrayList<TeamMember> TEAM_MEMBERS = new ArrayList<ExamplesApplication.TeamMember>();
	public static final String BASE_EXAMPLES_URL = "https://github.com/MasDennis/RajawaliExamples/blob/master/src/com/monyetmabuk/rajawali/tutorials/examples";

	@Override
	public void onCreate() {
		super.onCreate();

	
		
		
	}

	public static final class TeamMember {
		public int photo;
		public String name;
		public String favoriteBeer;
		public String link;

		protected TeamMember(int photo, String name, String about, String link) {
			this.photo = photo;
			this.name = name;
			this.favoriteBeer = about;
			this.link = link;
		}
	}

	public static final class ExampleItem {

		public final Class<? extends GlWorld> exampleClass;
		public final String title;
		public final String url;

		public ExampleItem(String title,
				Class<? extends GlWorld> exampleClass) {
			this.title = title;
			this.exampleClass = exampleClass;
			this.url = exampleClass.getSimpleName() + ".java";
		}

		public String getUrl(Category category) {
			switch (category) {
			case ABOUT:
				// About category has no example links
				return null;
			default:
				return BASE_EXAMPLES_URL + "/"
						+ category.name.toLowerCase(Locale.US) + "/" + url;
			}
		}
	}

}
