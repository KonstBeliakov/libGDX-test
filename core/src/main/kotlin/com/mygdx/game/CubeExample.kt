package com.mygdx.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.Model
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.math.Vector3

class CubeExample : ApplicationAdapter() {

    private lateinit var camera: PerspectiveCamera
    private lateinit var modelBatch: ModelBatch
    private lateinit var environment: Environment
    private lateinit var cubeModel: Model
    private lateinit var cubeInstance: ModelInstance

    override fun create() {
        // ModelBatch is responsible for batching and rendering 3D models
        modelBatch = ModelBatch()

        // Environment holds lights and other rendering attributes
        environment = Environment().apply {
            // Add ambient light
            set(ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1f))
            // Add a directional light
            add(DirectionalLight().set(Color.WHITE, Vector3(-1f, -0.8f, -0.2f)))
        }

        // Set up a simple Perspective Camera
        // The parameters are: field of view, viewport width, viewport height
        camera = PerspectiveCamera(67f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat()).apply {
            // Position the camera at (0, 0, 5)
            position.set(0f, 0f, 5f)
            // Look at the origin (0,0,0)
            lookAt(0f, 0f, 0f)
            near = 0.1f
            far = 300f
            update()
        }

        // Use ModelBuilder to create a simple cube
        val modelBuilder = ModelBuilder()
        cubeModel = modelBuilder.createBox(
            1f, 1f, 1f,               // width, height, depth of the cube
            // Material color and usage (positions, normals)
            com.badlogic.gdx.graphics.g3d.Material(ColorAttribute.createDiffuse(Color.GREEN)),
            (com.badlogic.gdx.graphics.VertexAttributes.Usage.Position or
                    com.badlogic.gdx.graphics.VertexAttributes.Usage.Normal).toLong()
        )

        // Create an instance of the model so we can position/rotate/scale it
        cubeInstance = ModelInstance(cubeModel)
    }

    override fun render() {
        // Clear the screen with a dark gray color
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)

        // Update the camera
        camera.update()

        // Start rendering with modelBatch
        modelBatch.begin(camera)

        // Render our cube with specific transformations
        // x, y, z, rotationX, rotationY, rotationZ
        renderCube(0f, 0f, 0f, 0f, Gdx.graphics.deltaTime * 50f, 0f)

        // Finish rendering
        modelBatch.end()
    }

    override fun dispose() {
        // Dispose of resources to avoid memory leaks
        modelBatch.dispose()
        cubeModel.dispose()
    }

    /**
     * Renders a cube at the given world coordinates (x, y, z)
     * with a rotation around the X, Y, and Z axes.
     *
     * @param x world X position
     * @param y world Y position
     * @param z world Z position
     * @param rotationX rotation angle in degrees around X axis
     * @param rotationY rotation angle in degrees around Y axis
     * @param rotationZ rotation angle in degrees around Z axis
     */
    fun renderCube(
        x: Float,
        y: Float,
        z: Float,
        rotationX: Float,
        rotationY: Float,
        rotationZ: Float
    ) {
        // Reset the transformation
        cubeInstance.transform.idt()

        // Translate the cube
        cubeInstance.transform.translate(x, y, z)

        // Rotate the cube around X, then Y, then Z
        cubeInstance.transform.rotate(Vector3.X, rotationX)
        cubeInstance.transform.rotate(Vector3.Y, rotationY)
        cubeInstance.transform.rotate(Vector3.Z, rotationZ)

        // Draw the cube
        modelBatch.render(cubeInstance, environment)
    }
}
