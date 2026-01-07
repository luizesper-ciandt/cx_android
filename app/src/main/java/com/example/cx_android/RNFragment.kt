package com.example.cx_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.facebook.react.ReactApplication
import com.facebook.react.ReactHost
import com.facebook.react.interfaces.fabric.ReactSurface

class RNFragment : Fragment() {

    private var reactSurface: ReactSurface? = null
    private var reactHost: ReactHost? = null

    companion object {
        private const val ARG_COMPONENT_NAME = "componentName"

        fun newInstance(componentName: String = "RNApp"): RNFragment {
            return RNFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_COMPONENT_NAME, componentName)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val app = requireActivity().application as? ReactApplication
            ?: return null

        reactHost = app.reactHost ?: return null

        val componentName = arguments?.getString(ARG_COMPONENT_NAME) ?: "RNApp"
        reactSurface = reactHost?.createSurface(requireActivity(), componentName, Bundle())
        reactSurface?.start()

        return reactSurface?.view
    }

    override fun onResume() {
        super.onResume()
        reactHost?.onHostResume(activity)
    }

    override fun onPause() {
        super.onPause()
        reactHost?.onHostPause(activity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        reactSurface?.stop()
        reactSurface = null
    }

    override fun onDestroy() {
        super.onDestroy()
        reactHost?.onHostDestroy(activity)
        reactHost = null
    }
}
