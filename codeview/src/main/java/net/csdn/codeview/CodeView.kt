package net.csdn.codeview

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.csdn.codeview.adapters.AbstractCodeAdapter
import net.csdn.codeview.adapters.CodeWithNotesAdapter
import net.csdn.codeview.adapters.Options
import net.csdn.codeview.views.BidirectionalScrollView

/**
 * @class CodeView
 *
 * Display code with syntax highlighting.
 *
 * @author Kirill Biakov
 */
class CodeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private val rvContent: RecyclerView
    private val adapter get() = rvContent.adapter as? AbstractCodeAdapter<*>

    /**
     * Primary constructor.
     */
    init {
        inflate(context, R.layout.layout_code_view, this)
        rvContent = findViewById<RecyclerView>(R.id.rv_content).apply {
            layoutManager = LinearLayoutManager(context)
            isNestedScrollingEnabled = true
        }
    }

    /**
     * View options accessor.
     */
    private val optionsOrDefault get() = adapter?.options ?: Options(context)

    /**
     * Initialize with options.
     *
     * @param options Options
     */
    fun setOptions(options: Options) = setAdapter(CodeWithNotesAdapter(context, options))

//    fun getOptions(): Options = optionsOrDefault

    /**
     * Update options or initialize if needed.
     *
     * @param options Options
     */
    fun updateOptions(options: Options) {
        adapter
            ?.let {
                it.options = options
            }
            ?: setOptions(options)
    }

    /**
     * Update options or initialize if needed.
     *
     * @param body Options mutator
     */
    fun updateOptions(body: Options.() -> Unit) =
        optionsOrDefault
            .apply(body)
            .apply(::updateOptions)

    // - Adapter

    /**
     * Initialize with adapter.
     *
     * Highlight code with defined programming language.
     * It holds the placeholder on view until code is not highlighted.
     *
     * @param adapter Adapter
     */
    fun setAdapter(adapter: AbstractCodeAdapter<*>) {
        rvContent.adapter = adapter.apply {
            highlight { notifyDataSetChanged() }
        }
    }

//    fun highlight() {
//        adapter?.apply { highlight { notifyDataSetChanged() } }
//    }

    /**
     * Update adapter or initialize if needed.
     *
     * @param adapter Adapter
     * @param isUseCurrent Use options that are already set or default
     */
    fun updateAdapter(adapter: AbstractCodeAdapter<*>, isUseCurrent: Boolean) {
        setAdapter(adapter.apply {
            if (isUseCurrent) {
                options = optionsOrDefault
            }
        })
    }

    // - Set code

    /**
     * Set code content. View is:
     * 1) not initialized (adapter or options is not set):
     *    prepare with default params & try to classify language
     * 2) initialized (with some params), language is:
     *    a) set: use defined
     *    b) not set: try to classify
     *
     * @param code Code content
     */
    fun setCode(code: String) = setCode(code, null)

    /**
     * Set code content. View is:
     * 1) not initialized: prepare with default params
     * 2) initialized (with some params): set new language
     *
     * @param code Code content
     * @param language Programming language
     */
    fun setCode(code: String, language: String? = null) {
        val options = optionsOrDefault.apply {
            this.language = language
        }
        (adapter ?: CodeWithNotesAdapter(context, options)
            .apply(::setAdapter))
            .updateCode(code)
    }
}

/**
 * Provide listener to code line clicks.
 */
interface OnCodeLineClickListener {
    fun onCodeLineClicked(n: Int, line: String)
    fun onCodeLineLongClicked(n: Int, line: String)
}
