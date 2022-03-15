package xyz.cloudcoffee.atlas.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import xyz.cloudcoffee.atlas.databinding.DialogErrorBinding

class ErrorDialog(val erorrMsg : String) : AppCompatDialogFragment() {
    private lateinit var vbind : DialogErrorBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vbind = DialogErrorBinding.inflate(inflater)
        return vbind.root
    }
}