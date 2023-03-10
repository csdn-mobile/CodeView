package net.csdn.codeview.demo;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import net.csdn.codeview.CodeView;
import net.csdn.codeview.adapters.Format;
import net.csdn.codeview.adapters.Options;
import net.csdn.codeview.highlight.ColorTheme;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class ListingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listings);

        final CodeView codeView = findViewById(R.id.code_view);

        /*
         * 1: set code content
         */

        // auto language recognition
//        codeView.setCode(getString(R.string.listing_py));

        // specify language for code listing
//        codeView.setCode(getString(R.string.listing_py), "py");

        /*
         * 2: working with options
         */

        // short initialization with default params (can be expanded using with() methods)
//        codeView.setOptions(Options.Default.get(this)
//                .withCode(R.string.listing_py)
//                .withTheme(ColorTheme.MONOKAI));

        // expanded form of initialization
//        codeView.setOptions(new Options(
//                this,                                   // context
//                getString(R.string.listing_js),         // code
//                "js",                                   // language
//                ColorTheme.MONOKAI.theme(),             // theme (data)
//                Format.Default.getCompact(),            // format
//                true,                                   // animate on highlight
//                true,                                   // shadows visible
//                true,                                   // shortcut
//                getString(R.string.show_all),           // shortcut note
//                10,                                     // max lines
//                new OnCodeLineClickListener() {         // line click listener
//                    @Override
//                    public void onCodeLineClicked(int n, @NotNull String line) {
//                        Log.i("ListingsActivity", "On " + (n + 1) + " line clicked");
//                    }
//                }));

        /*
         * 3: color themes
         */

//        codeView.getOptions().setTheme(ColorTheme.SOLARIZED_LIGHT);

        // custom theme
//        ColorThemeData myTheme = ColorTheme.SOLARIZED_LIGHT.theme()
//                .withBgContent(android.R.color.black)
//                .withNoteColor(android.R.color.white);
//
//        codeView.getOptions().setTheme(myTheme);

        /*
         * 4: custom adapter with footer views
         */

//        final CustomAdapter myAdapter = new CustomAdapter(this, getString(R.string.listing_md));
//        codeView.setAdapter(myAdapter);
//        codeView.getOptions()
//                .withLanguage("md")
//                .addCodeLineClickListener(new OnCodeLineClickListener() {
//                    @Override
//                    public void onCodeLineClicked(int n, @NotNull String line) {
//                        myAdapter.addFooterEntity(n, new CustomAdapter.CustomModel("Line " + (n + 1), line));
//                    }
//                });

        /*
         * 5: diff adapter with footer views
         */

//        final CodeWithDiffsAdapter diffsAdapter = new CodeWithDiffsAdapter(this);
//        codeView.getOptions()
//                .withLanguage("python")
//                .setCode(getString(R.string.listing_py));
//        codeView.updateAdapter(diffsAdapter);
//
//        diffsAdapter.addFooterEntity(16, new DiffModel(getString(R.string.py_addition_16), true));
//        diffsAdapter.addFooterEntity(11, new DiffModel(getString(R.string.py_deletion_11), false));

        /*
         * 6: shortcut adapter with footer views
         */

//        codeView.getOptions()
//                .shortcut(10, "Show all");

        // - Playground
//
        // custom theme
//
//        ColorTheme colorTheme = new ColorThemeData(
//                new SyntaxColors()
//        );
        codeView.setCode("" +
                "public class HollowDiamond {\n" +
                "    public static void main(String[] args) {\n" +
                "        int size = 5;\n" +
                "           for (int i = 0; i < size; i++) {\n" +
                "            for (int j = 0; j < size - i - 1; j++) {\n" +
                "                System.out.print(\" \");\n" +
                "            }\n" +
                "            for (int j = 0; j < 2 * i + 1; j++) {\n" +
                "                if (i == 0 || i == size - 1 || j == 0 || j == 2 * i) {\n" +
                "                    System.out.print(\"*\");\n" +
                "                } else {\n" +
                "                    System.out.print(\" \");\n" +
                "                }\n" +
                "            }\n" +
                "            System.out.println();\n" +
                "        }\n" +
                "    }\n" +
                "}", "java");
        codeView.updateOptions(new Function1<Options, Unit>() {
            @Override
            public Unit invoke(Options options) {
                options.isShowLineNumber(true)
                        .withFormat(new Format(1f, 20, 8, 14))
                        .withTheme(ColorTheme.CSDN_DAY);
                return null;
            }
        });
    }
}
