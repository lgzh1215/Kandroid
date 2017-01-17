package moe.lpj.kandroid.receiver

class HideOverlaysReceiver : eu.chainfire.libsuperuser.HideOverlaysReceiver() {

    override fun onHideOverlays(hide: Boolean) {
        //TODO
        if (hide) {
            // hide overlays, if any
        } else {
            // show previously hidden overlays, if any
        }
    }
}
