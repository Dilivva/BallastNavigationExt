import SwiftUI
import shared

@main
struct iOSApp: App {
	var body: some Scene {
		WindowGroup {
            ContentView().onOpenURL { link in
                //Set up to support URL scheme
                DeepLinkResult.companion.setUrl(url: link.absoluteString)
            }
		}
	}
}
