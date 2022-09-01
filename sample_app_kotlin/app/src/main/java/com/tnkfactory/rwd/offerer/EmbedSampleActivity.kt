package com.tnkfactory.rwd.offerer

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.tnkfactory.ad.*
import com.tnkfactory.rwd.offerer.databinding.ActivityCustomBinding
import com.tnkfactory.rwd.offerer.databinding.ActivityEmbedBinding

class EmbedSampleActivity : AppCompatActivity() {

    var arrAdType: Array<AdListType> = arrayOf(AdListType.ALL, AdListType.PPI, AdListType.CPS)
    var adStyle: TnkLayout = TemplateLayoutUtils.getBlueStyle_01()

    var adView: AdListTabView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityEmbedBinding = DataBindingUtil.setContentView(this, R.layout.activity_embed)


        setAdTypeSpinner(binding.spAdType)
        setLayoutStyleSpinner(binding.spAdStyle)

        binding.btnShowOfferer.setOnClickListener {
            binding.flContent.removeAllViews()
            adView = TnkSession.createAdListTabView(this@EmbedSampleActivity, "basic", adStyle, *arrAdType)
            binding.flContent.addView(adView)
        }

    }


    fun setAdTypeSpinner(spinnerAdType: Spinner) {
        setSpinnerItem(spinnerAdType, R.array.ad_type) { pos ->
            when (pos) {
                0 -> arrAdType = arrayOf(AdListType.ALL, AdListType.PPI, AdListType.CPS)
                1 -> arrAdType = arrayOf(AdListType.PPI)
                2 -> arrAdType = arrayOf(AdListType.CPS)
            }
        }
    }

    fun setLayoutStyleSpinner(spinnerAdStyle: Spinner) {
        setSpinnerItem(spinnerAdStyle, R.array.ad_style) { pos ->
            when (pos) {
                0 -> adStyle = TemplateLayoutUtils.getBlueTabStyle_01()
                1 -> adStyle = TemplateLayoutUtils.getBlueTabStyle_02()
                2 -> adStyle = TemplateLayoutUtils.getBlueTabStyle_03()
                3 -> adStyle = TemplateLayoutUtils.getBlueTabStyle_04()
                4 -> adStyle = TemplateLayoutUtils.getBlueTabStyle_05()
                5 -> adStyle = TemplateLayoutUtils.getBlueTabStyle_06()
                6 -> adStyle = TemplateLayoutUtils.getBlueTabStyle_07()
                7 -> adStyle = TemplateLayoutUtils.getBlueTabStyle_08()
                8 -> adStyle = TemplateLayoutUtils.getRedTabStyle_01()
                9 -> adStyle = TemplateLayoutUtils.getRedTabStyle_02()
                10 -> adStyle = TemplateLayoutUtils.getRedTabStyle_03()
                11 -> adStyle = TemplateLayoutUtils.getRedTabStyle_04()
                12 -> adStyle = TemplateLayoutUtils.getRedTabStyle_05()
                13 -> adStyle = TemplateLayoutUtils.getRedTabStyle_06()
                14 -> adStyle = TemplateLayoutUtils.getRedTabStyle_07()
                15 -> adStyle = TemplateLayoutUtils.getRedTabStyle_08()
            }
        }
    }

    private fun setSpinnerItem(view: Spinner, textArrayResId: Int, onItemSelected: (pos: Int) -> Unit) {
        ArrayAdapter.createFromResource(
            this, textArrayResId, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            view.adapter = adapter
        }
        view.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                onItemSelected(pos)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }


    companion object {
        fun start(context: Context) {
            val it = Intent(context, EmbedSampleActivity::class.java)
            context.startActivity(it)
        }
    }

}