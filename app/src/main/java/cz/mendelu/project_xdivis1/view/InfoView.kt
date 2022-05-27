package cz.mendelu.project_xdivis1.view
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import cz.mendelu.project_xdivis1.R
import cz.mendelu.project_xdivis1.databinding.ViewInfoBinding
class InfoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttrs: Int = 0
) : FrameLayout(context, attrs, defStyleAttrs)
{
    init {
        init(context, attrs)
    }
    private lateinit var binding: ViewInfoBinding
    var value: String
        get() = binding.value.text.toString()
        set(value){
            binding.value.setText(value)
        }

    private fun init(context: Context, attrs: AttributeSet?){
        binding = ViewInfoBinding.inflate(LayoutInflater.from(context), this, true)
        if (attrs != null){
            loadAttributes(attrs)
        }
        hideClearButton()
    }
    private fun loadAttributes(attrs: AttributeSet){
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.InfoView)
        val value = attributes.getString(R.styleable.InfoView_value)
        val header = attributes.getString(R.styleable.InfoView_header)
        val image = attributes.getDrawable(R.styleable.InfoView_image)
        binding.value.text = value
        binding.header.text = header
        binding.infoImage.setImageDrawable(image)
        attributes.recycle()
    }

    fun setOnClearClickListener(listener: OnClickListener){
        binding.clearButton.setOnClickListener(listener)
    }

    fun hideClearButton(){
        binding.clearButton.visibility = View.GONE
    }

    fun showClearButton(){
        binding.clearButton.visibility = View.VISIBLE
    }

}