package edu.kwjw.you.util

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar.SnackbarLayout

@Suppress("UNUSED_PARAMETER")
class OverlapBehavior(context: Context, attrs: AttributeSet) :
    CoordinatorLayout.Behavior<FloatingActionButton>() {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: FloatingActionButton,
        dependency: View
    ) = dependency is SnackbarLayout


    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: FloatingActionButton,
        dependency: View
    ) = false
}