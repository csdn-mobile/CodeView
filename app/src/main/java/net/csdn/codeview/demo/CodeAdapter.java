package net.csdn.codeview.demo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.csdn.codeview.CodeView;
import net.csdn.codeview.OnCodeLineClickListener;
import net.csdn.codeview.adapters.Format;
import net.csdn.codeview.adapters.Options;
import net.csdn.codeview.highlight.ColorTheme;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class CodeAdapter extends RecyclerView.Adapter<CodeAdapter.CodeViewHolder> {

    @NonNull
    @Override
    public CodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CodeViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_code, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CodeViewHolder holder, int position) {
        holder.setData();
    }

    @Override
    public int getItemCount() {
        return 200;
    }

    static class CodeViewHolder extends RecyclerView.ViewHolder {
        CodeView codeView;

        public CodeViewHolder(@NonNull View itemView) {
            super(itemView);
            codeView = itemView.findViewById(R.id.code_view);
            codeView.setOptions(Options.Default.get(itemView.getContext())
                    .isShowLineNumber(true)
                    .withAutoHighlight(false)
                    .withTheme(ColorTheme.CSDN_DAY)
                    .withFormat(new Format(1f, 20, 8, 14))
                    .addCodeLineClickListener(new OnCodeLineClickListener() {
                        @Override
                        public void onCodeLineClicked(int i, @NonNull String s) {
                            codeView.highlight();
                            Toast.makeText(itemView.getContext(), codeView.getLineNumber() + "=====", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCodeLineLongClicked(int i, @NonNull String s) {
                        }
                    }));
        }

        public void setData() {
            codeView.setCode("????????????Python????????????????????????\n\n???????????????????????????????????????\n\n```\nclass Circle:\n    def __init__(self, radius):\n        self.radius = radius\n\n    def area(self):\n        return 3.14 * self.radius ** 2\n\n    def circumference(self):\n        return 2 * 3.14 * self.radius\n```\n\n???????????????????????????????????????????????????`Circle`????????????????????????????????????`__init__`????????????????????????`radius`???????????????????????????????????????`self.radius`??????\n\n??????????????????????????????`area`???`circumference`?????????????????????????????????????????????????????????\n\n???????????????\n```\n>>> c = Circle(10)\n>>> c.area()\n314.0\n>>> c.circumference()\n62.800000000000004\n```\n", "java");
//            codeView.setCode("" +
//                    "public class ListingsActivity extends AppCompatActivity {\n" +
//                    "\n" +
//                    "    @Override\n" +
//                    "    protected void onCreate(@Nullable Bundle savedInstanceState) {\n" +
//                    "        super.onCreate(savedInstanceState);\n" +
//                    "        setContentView(R.layout.activity_listings);\n" +
//                    "\n" +
//                    "        final CodeView codeView = (CodeView) findViewById(R.id.code_view);\n" +
//                    "\n" +
//                    "        /*\n" +
//                    "         * 1: set code content\n" +
//                    "         */\n" +
//                    "\n" +
//                    "        // auto language recognition\n" +
//                    "        codeView.setCode(getString(R.string.listing_js));\n" +
//                    "\n" +
//                    "        // specify language for code listing\n" +
//                    "        codeView.setCode(getString(R.string.listing_py), \"py\");" +
//                    "    }\n" +
//                    "}", "java");
//            codeView.setCode("", "java");
//            codeView.updateOptions(new Function1<Options, Unit>() {
//                @Override
//                public Unit invoke(Options options) {
//                    options.isShowLineNumber(true)
//                            .withFormat(new Format(1f, 20, 8, 14))
//                            .withTheme(ColorTheme.CSDN_DAY)
//                            .addCodeLineClickListener(new OnCodeLineClickListener() {
//                                @Override
//                                public void onCodeLineClicked(int n, @NonNull String line) {
//                                    Toast.makeText(itemView.getContext(), codeView.getLineNumber() + "=====", Toast.LENGTH_SHORT).show();
//                                }
//
//                                @Override
//                                public void onCodeLineLongClicked(int n, @NonNull String line) {
//
//                                }
//                            });
//                    return null;
//                }
//            });

        }
    }
}
