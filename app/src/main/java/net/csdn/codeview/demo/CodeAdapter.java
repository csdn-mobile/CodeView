package net.csdn.codeview.demo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.csdn.codeview.CodeView;
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
        }

        public void setData() {
//            codeView.setOptions(Options.Default.get(itemView.getContext())
//                    .withCode(R.string.listing_py)
//                    .withTheme(ColorTheme.MONOKAI));
            codeView.setCode("" +
                    "public class ListingsActivity extends AppCompatActivity {\n" +
                    "\n" +
                    "    @Override\n" +
                    "    protected void onCreate(@Nullable Bundle savedInstanceState) {\n" +
                    "        super.onCreate(savedInstanceState);\n" +
                    "        setContentView(R.layout.activity_listings);\n" +
                    "\n" +
                    "        final CodeView codeView = (CodeView) findViewById(R.id.code_view);\n" +
                    "\n" +
                    "        /*\n" +
                    "         * 1: set code content\n" +
                    "         */\n" +
                    "\n" +
                    "        // auto language recognition\n" +
                    "        codeView.setCode(getString(R.string.listing_js));\n" +
                    "\n" +
                    "        // specify language for code listing\n" +
                    "        codeView.setCode(getString(R.string.listing_py), \"py\");" +
                    "    }\n" +
                    "}", "java");
            codeView.updateOptions(new Function1<Options, Unit>() {
                @Override
                public Unit invoke(Options options) {
                    options.withTheme(ColorTheme.SOLARIZED_LIGHT)
                            .setShortcut(false);
                    return null;
                }
            });

        }
    }
}
