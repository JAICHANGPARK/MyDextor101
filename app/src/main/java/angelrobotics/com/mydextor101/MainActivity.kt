package angelrobotics.com.mydextor101

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Logger.addLogAdapter(AndroidLogAdapter())


        setContentView(R.layout.activity_main)

        camera_permission_button.setOnClickListener {
            Dexter.withActivity(this@MainActivity).withPermission(Manifest.permission.CAMERA)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        Toast.makeText(this@MainActivity, "Permission Granted", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permission: PermissionRequest?,
                        token: PermissionToken?
                    ) {

                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        Toast.makeText(this@MainActivity, "Permission Camera need to use Camera", Toast.LENGTH_SHORT)
                            .show()
                    }

                }).check()
        }

        all_permissions_button.setOnClickListener {
            Dexter.withActivity(this@MainActivity)
                .withPermissions(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.RECORD_AUDIO
                )
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {

                        report?.grantedPermissionResponses?.forEach {
                            Log.e("MainActivity", "" + it.permissionName)
                            Logger.d(it.permissionName)
                        }

                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: MutableList<PermissionRequest>?,
                        token: PermissionToken?
                    ) {

                    }

                }).check()
        }

    }
}
