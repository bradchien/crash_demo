package dev.flutter.multipleflutters

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.android.RenderMode

/**
 * This is an Activity that displays one instance of Flutter.
 *
 * EngineBindings is used to bridge communication between the Flutter instance and the DataModel.
 */
class MainActivity : AppCompatActivity(), EngineBindingsDelegate {
	private val engineBindings: EngineBindings by lazy {
		EngineBindings(activity = this, entrypoint = "main")
	}

	private var flutterFragment: FlutterFragment? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		engineBindings.attach()

		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		flutterFragment =
			FlutterFragment.withCachedEngine("shared_engine").renderMode(RenderMode.texture).build()
		val transaction = supportFragmentManager.beginTransaction()
		transaction.add(R.id.container, flutterFragment!!, "Flutter_Page")
		transaction.commit()
	}

	override fun onDestroy() {
		super.onDestroy()
		engineBindings.detach()
	}

	override fun onPostResume() {
		super.onPostResume()
		flutterFragment!!.onPostResume()
	}

	override fun onNewIntent(intent: Intent) {
		super.onNewIntent(intent)
		flutterFragment!!.onNewIntent(intent)
	}

	override fun onBackPressed() {
		flutterFragment!!.onBackPressed()
	}

	override fun onRequestPermissionsResult(
		requestCode: Int, permissions: Array<String?>, grantResults: IntArray
	) {
		flutterFragment!!.onRequestPermissionsResult(
			requestCode, permissions, grantResults
		)
	}

	override fun onUserLeaveHint() {
		flutterFragment!!.onUserLeaveHint()
	}

	override fun onTrimMemory(level: Int) {
		super.onTrimMemory(level)
		flutterFragment!!.onTrimMemory(level)
	}
}
