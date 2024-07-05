import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.bitlasoft.trackingo.R

class MainViewModel(private val state: SavedStateHandle) : ViewModel() {
    companion object {
        private const val KEY_CURRENT_FRAGMENT = "current_fragment"
    }

    var currentFragment: Int
        get() = state[KEY_CURRENT_FRAGMENT] ?: R.id.firstFragment // Default to first fragment
        set(value) {
            state[KEY_CURRENT_FRAGMENT] = value
        }
}
