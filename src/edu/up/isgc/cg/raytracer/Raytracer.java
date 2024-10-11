package edu.up.isgc.cg.raytracer;

import edu.up.isgc.cg.raytracer.lights.Light;
import edu.up.isgc.cg.raytracer.lights.PointLight;
import edu.up.isgc.cg.raytracer.objects.*;
import edu.up.isgc.cg.raytracer.tools.OBJReader;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;

import static java.lang.Math.clamp;

/**
 * Main class for the ray tracer application.
 * It sets up scenes, lights, and objects, and performs the ray tracing to generate an image.
 */
public class Raytracer {

    public static void main(String[] args) {
        System.out.println(new Date());

        // Define materials
        Material mirrorMat = new Material(0.9, 0.0, 1.0, 200);
        Material matteMat = new Material(0, 0.0, 1.0, 150);
        Material glassMat = new Material(0, 1.0, 2, 200);
        Material waterMat = new Material(0.5, 0.8, 1.33, 100);

        // Set up the first scene
        Scene Render01 = new Scene();
        Render01.setCamera(new Camera(new Vector3D(0, 2, -6), 60, 60, 4000, 4000, 0.6, 50, null));
        Render01.addLight(new PointLight(new Vector3D(0.0, 5.0, -5.0), Color.WHITE, 2.5, null));

        // Add objects to the first scene
        Render01.addObject(new Model3D(new Vector3D(0, -1, 0),
                new Triangle[]{
                        new Triangle(new Vector3D(-100, 0, -100), new Vector3D(100, 0, -100), new Vector3D(100, 0, 100)),
                        new Triangle(new Vector3D(-100, 0, -100), new Vector3D(100, 0, 100), new Vector3D(-100, 0, 100))
                },
                Color.darkGray, waterMat));
        Render01.addObject(new Model3D(new Vector3D(0, -1, 0),
                new Triangle[]{
                        new Triangle(new Vector3D(-100, -50, 20), new Vector3D(100, -50, 20), new Vector3D(100, 50, 20)),
                        new Triangle(new Vector3D(-100, -50, 20), new Vector3D(100, 50, 20), new Vector3D(-100, 50, 20))
                },
                new Color(17, 83, 115), matteMat));
        Render01.addObject(OBJReader.getModel3D("Cato.obj", new Vector3D(-1, -1.1, -2), Color.DARK_GRAY, matteMat));
        Render01.addObject(OBJReader.getModel3D("aircraft.obj", new Vector3D(-2, 3.0, 2), Color.DARK_GRAY, matteMat));
        Render01.addObject(OBJReader.getModel3D("aircraft.obj", new Vector3D(2, 3.0, 2), Color.DARK_GRAY, matteMat));
        Render01.addObject(OBJReader.getModel3D("Tower.obj", new Vector3D(0, -1, 12), new Color(185, 182, 63), matteMat));

        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 5; i++) {
                Render01.addObject(OBJReader.getModel3D("Cato.obj", new Vector3D(i, -1.1, j), Color.WHITE, matteMat));
            }
        }
        for (int j = 0; j < 3; j++) {
            for (int i = -5; i < -1; i++) {
                Render01.addObject(OBJReader.getModel3D("Cato.obj", new Vector3D(i, -1.1, j), Color.RED, matteMat));
            }
        }

        // Set up the second scene
        Scene Render02 = new Scene();
        Render02.setCamera(new Camera(new Vector3D(0, 1, -4.5), 60, 60, 4000, 4000, 0.6, 50.0, null));
        Render02.addLight(new PointLight(new Vector3D(0.0, 4.0, -5.0), Color.WHITE, 1.75, null));

        // Add objects to the second scene
        Render02.addObject(new Sphere(new Vector3D(-1, 1.7, -1.0), 0.5, Color.WHITE, mirrorMat));
        Render02.addObject(OBJReader.getModel3D("Rubik.obj", new Vector3D(3, 0, 4.5), Color.BLUE, matteMat));
        Render02.addObject(OBJReader.getModel3D("Horse.obj", new Vector3D(2.7, 0, 4.5), Color.YELLOW, matteMat));
        Render02.addObject(OBJReader.getModel3D("Heart.obj", new Vector3D(-2, 0, 3.5), Color.RED, matteMat));
        Render02.addObject(new Sphere(new Vector3D(1, 1, 0), 0.7, Color.WHITE, glassMat));

        Render02.addObject(new Model3D(new Vector3D(0, 0, 0), new Triangle[]{
                // Floor
                new Triangle(new Vector3D(-5, 0, -5), new Vector3D(5, 0, -5), new Vector3D(5, 0, 5)),
                new Triangle(new Vector3D(-5, 0, -5), new Vector3D(5, 0, 5), new Vector3D(-5, 0, 5)),
                // Ceiling
                new Triangle(new Vector3D(-5, 4, -5), new Vector3D(5, 4, -5), new Vector3D(5, 4, 5)),
                new Triangle(new Vector3D(-5, 4, -5), new Vector3D(5, 4, 5), new Vector3D(-5, 4, 5)),
                // Walls
                new Triangle(new Vector3D(-5, 0, -5), new Vector3D(-5, 4, -5), new Vector3D(-5, 4, 5)),
                new Triangle(new Vector3D(-5, 0, -5), new Vector3D(-5, 4, 5), new Vector3D(-5, 0, 5)),
                new Triangle(new Vector3D(5, 0, -5), new Vector3D(5, 4, -5), new Vector3D(5, 4, 5)),
                new Triangle(new Vector3D(5, 0, -5), new Vector3D(5, 4, 5), new Vector3D(5, 0, 5)),
                new Triangle(new Vector3D(-5, 0, -5), new Vector3D(-5, 4, -5), new Vector3D(5, 4, -5)),
                new Triangle(new Vector3D(-5, 0, -5), new Vector3D(5, 4, -5), new Vector3D(5, 0, -5)),
                new Triangle(new Vector3D(-5, 0, 5), new Vector3D(-5, 4, 5), new Vector3D(5, 4, 5)),
                new Triangle(new Vector3D(-5, 0, 5), new Vector3D(5, 4, 5), new Vector3D(5, 0, 5))
        }, Color.WHITE, mirrorMat));

        // Set up the third scene
        Scene Render03 = new Scene();
        Render03.setCamera(new Camera(new Vector3D(0, 2, -4), 60, 60, 800, 800, 0.6, 50.0, null));
        Render03.addLight(new PointLight(new Vector3D(0.0, 5.0, -3.5), Color.WHITE, 3.5, null));

        Render03.addObject(new Model3D(new Vector3D(0, 0, 1),
                new Triangle[]{
                        new Triangle(new Vector3D(-100, 0, -100), new Vector3D(100, 0, -100), new Vector3D(100, 0, 100)),
                        new Triangle(new Vector3D(-100, 0, -100), new Vector3D(100, 0, 100), new Vector3D(-100, 0, 100))
                },
                Color.LIGHT_GRAY, mirrorMat));
        Render03.addObject(new Model3D(new Vector3D(0, -1, 0),
                new Triangle[]{
                        new Triangle(new Vector3D(-100, -50, 25), new Vector3D(100, -50, 25), new Vector3D(100, 50, 25)),
                        new Triangle(new Vector3D(-100, -50, 25), new Vector3D(100, 50, 25), new Vector3D(-100, 50, 25))
                },
                Color.DARK_GRAY, matteMat));

        // Render the third scene and save the image
        BufferedImage image = raytrace(Render03);

        File outputImage = new File("image.png");
        try {
            ImageIO.write(image, "png", outputImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(new Date());
    }

    /**
     * Performs the ray tracing algorithm for the given scene.
     *
     * @param scene The scene to be ray traced.
     * @return A BufferedImage containing the rendered image.
     */
    public static BufferedImage raytrace(Scene scene) {
        Camera mainCamera = scene.getCamera();
        BufferedImage image = new BufferedImage(mainCamera.getResolutionWidth(), mainCamera.getResolutionHeight(), BufferedImage.TYPE_INT_RGB);
        Vector3D[][] posRaytrace = mainCamera.calculatePositionsToRay();

        ExecutorService executorService = Executors.newFixedThreadPool(12);
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < posRaytrace.length; i++) {
            final int finalI = i;
            for (int j = 0; j < posRaytrace[i].length; j++) {
                final int finalJ = j;
                executorService.submit(() -> {
                    double x = posRaytrace[finalI][finalJ].getX();
                    double y = posRaytrace[finalI][finalJ].getY();
                    double z = posRaytrace[finalI][finalJ].getZ();

                    Ray ray = new Ray(mainCamera.getPosition(), new Vector3D(x, y, z));
                    Color pixelColor = traceRay(scene, ray, 3);

                    setRGB(image, finalI, finalJ, pixelColor);
                });
            }
        }

        executorService.shutdown();
        try {
            while (!executorService.awaitTermination(1, TimeUnit.MINUTES)) {
                long elapsedTime = System.currentTimeMillis() - startTime;
                System.out.println("Rendering... elapsed time: " + elapsedTime / 1000 + " seconds");
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            throw new RuntimeException(e);
        }

        return image;
    }

    /**
     * Traces a ray through the scene, calculating the color of the pixel it intersects.
     *
     * @param scene The scene to trace the ray through.
     * @param ray The ray to be traced.
     * @param depth The depth of recursion for reflections and refractions.
     * @return The color of the pixel at the ray's intersection point.
     */
    public static Color traceRay(Scene scene, Ray ray, int depth) {
        if (depth <= 0) {
            return Color.BLACK;
        }

        Camera mainCamera = scene.getCamera();
        double[] nearFarPlanes = mainCamera.getNearFarPlanes();
        List<Object3D> objects = scene.getObjects();
        List<Light> lights = scene.getLights();
        Vector3D pos = mainCamera.getPosition();
        double cameraZ = pos.getZ();

        Color ambientLight = new Color(50, 50, 50);
        double ambientIntensity = 0.2;

        Intersection closestIntersection = raycast(ray, objects, null, new double[]{cameraZ + nearFarPlanes[0], cameraZ + nearFarPlanes[1]});
        Color pixelColor = new Color((int) (ambientLight.getRed() * ambientIntensity), (int) (ambientLight.getGreen() * ambientIntensity), (int) (ambientLight.getBlue() * ambientIntensity));

        if (closestIntersection != null) {
            Color objColor = closestIntersection.getObject().getColor();
            Material material = closestIntersection.getObject().getMaterial();
            double shininess = material.getShininess();
            double reflectivity = material.getReflectivity();
            double transparency = material.getTransparency();
            double refractiveIndex = material.getIor();

            double epsilon = 0.001;

            for (Light light : lights) {
                Vector3D shadowRayOrigin = Vector3D.add(closestIntersection.getPosition(), Vector3D.scalarMultiplication(closestIntersection.getNormal(), epsilon));
                Vector3D shadowRayDirection = Vector3D.normalize(Vector3D.substract(light.getPosition(), shadowRayOrigin));

                Ray rayShadow = new Ray(shadowRayOrigin, shadowRayDirection);

                if (raycast(rayShadow, objects, closestIntersection.getObject(), new double[]{cameraZ + nearFarPlanes[0], cameraZ + nearFarPlanes[1]}) != null) {
                    continue;
                }

                double nDotL = light.getNDotL(closestIntersection);
                if (nDotL <= 0) {
                    continue;
                }

                Color lightColor = light.getColor();
                double intensity = light.getIntensity() * nDotL;

                if (light instanceof PointLight) {
                    double distanceToLight = Vector3D.magnitude(Vector3D.substract(light.getPosition(), closestIntersection.getPosition()));
                    double constantAttenuation = 1.0;
                    double linearAttenuation = 0.09;
                    double quadraticAttenuation = 0.032;
                    double falloff = 1.0 / (constantAttenuation + linearAttenuation * distanceToLight + quadraticAttenuation * distanceToLight * distanceToLight);
                    intensity *= falloff;
                }

                double[] lightColors = new double[]{lightColor.getRed() / 255.0, lightColor.getGreen() / 255.0, lightColor.getBlue() / 255.0};
                double[] diffuseColors = new double[]{objColor.getRed() / 255.0, objColor.getGreen() / 255.0, objColor.getBlue() / 255.0};
                for (int colorIndex = 0; colorIndex < diffuseColors.length; colorIndex++) {
                    diffuseColors[colorIndex] *= intensity * lightColors[colorIndex];
                }

                Vector3D viewDirection = Vector3D.normalize(Vector3D.substract(mainCamera.getPosition(), closestIntersection.getPosition()));
                Vector3D halfVector = Vector3D.normalize(Vector3D.add(viewDirection, shadowRayDirection));
                double nDotH = Math.max(0.0, Vector3D.dotProduct(closestIntersection.getNormal(), halfVector));
                double specularIntensity = Math.pow(nDotH, shininess);

                double[] specularColors = new double[]{lightColor.getRed() / 255.0, lightColor.getGreen() / 255.0, lightColor.getBlue() / 255.0};
                for (int colorIndex = 0; colorIndex < specularColors.length; colorIndex++) {
                    specularColors[colorIndex] *= specularIntensity;
                }

                Color diffuse = new Color((float) clamp(diffuseColors[0], 0.0, 1.0), (float) clamp(diffuseColors[1], 0.0, 1.0), (float) clamp(diffuseColors[2], 0.0, 1.0));
                Color specular = new Color((float) clamp(specularColors[0], 0.0, 1.0), (float) clamp(specularColors[1], 0.0, 1.0), (float) clamp(specularColors[2], 0.0, 1.0));

                pixelColor = addColor(pixelColor, diffuse);
                pixelColor = addColor(pixelColor, specular);
            }

            // Reflection
            if (reflectivity > 0) {
                Vector3D reflectionDirection = Vector3D.normalize(Vector3D.substract(ray.getDirection(), Vector3D.scalarMultiplication(closestIntersection.getNormal(), 2 * Vector3D.dotProduct(ray.getDirection(), closestIntersection.getNormal()))));

                Ray reflectionRay = new Ray(Vector3D.add(closestIntersection.getPosition(), Vector3D.scalarMultiplication(reflectionDirection, epsilon)), reflectionDirection);
                Color reflectionColor = traceRay(scene, reflectionRay, depth - 1);

                pixelColor = addColor(pixelColor, new Color((int) (reflectionColor.getRed() * reflectivity), (int) (reflectionColor.getGreen() * reflectivity), (int) (reflectionColor.getBlue() * reflectivity)));
            }

            // Refraction
            if (transparency > 0) {
                double eta = 1.0; // Assume air to start with
                Vector3D normal = closestIntersection.getNormal();
                double n1 = eta;
                double n2 = refractiveIndex;

                if (Vector3D.dotProduct(ray.getDirection(), normal) > 0) {
                    normal = Vector3D.scalarMultiplication(normal, -1);
                    n1 = refractiveIndex;
                    n2 = eta;
                }

                Vector3D refractedDirection = refract(ray.getDirection(), normal, n1, n2);

                if (refractedDirection != null) {
                    Ray refractedRay = new Ray(Vector3D.add(closestIntersection.getPosition(), Vector3D.scalarMultiplication(refractedDirection, epsilon)), refractedDirection);
                    Color refractedColor = traceRay(scene, refractedRay, depth - 1);

                    pixelColor = addColor(pixelColor, new Color(
                            (int) (refractedColor.getRed() * transparency),
                            (int) (refractedColor.getGreen() * transparency),
                            (int) (refractedColor.getBlue() * transparency)
                    ));
                }
            }
        }

        return pixelColor;
    }

    /**
     * Sets the RGB value of a pixel in the image.
     *
     * @param image The image to modify.
     * @param x The x coordinate of the pixel.
     * @param y The y coordinate of the pixel.
     * @param pixelColor The color to set the pixel to.
     */
    public static synchronized void setRGB(BufferedImage image, int x, int y, Color pixelColor) {
        image.setRGB(x, y, pixelColor.getRGB());
    }

    /**
     * Adds two colors together, clamping each component to the range [0, 1].
     *
     * @param original The original color.
     * @param otherColor The color to add to the original color.
     * @return The resulting color.
     */
    public static Color addColor(Color original, Color otherColor) {
        float red = (float) clamp((original.getRed() / 255.0) + (otherColor.getRed() / 255.0), 0.0, 1.0);
        float green = (float) clamp((original.getGreen() / 255.0) + (otherColor.getGreen() / 255.0), 0.0, 1.0);
        float blue = (float) clamp((original.getBlue() / 255.0) + (otherColor.getBlue() / 255.0), 0.0, 1.0);
        return new Color(red, green, blue);
    }

    /**
     * Casts a ray through the scene, finding the closest intersection point.
     *
     * @param ray The ray to cast.
     * @param objects The objects in the scene.
     * @param caster The object that cast the ray (to avoid self-intersection).
     * @param clippingPlanes The near and far clipping planes.
     * @return The closest intersection, or null if no intersection occurs.
     */
    public static Intersection raycast(Ray ray, List<Object3D> objects, Object3D caster, double[] clippingPlanes) {
        Intersection closestIntersection = null;

        for (int i = 0; i < objects.size(); i++) {
            Object3D currObj = objects.get(i);
            if (caster == null || !currObj.equals(caster)) {
                Intersection intersection = currObj.getIntersection(ray);
                if (intersection != null) {
                    double distance = intersection.getDistance();
                    double intersectionZ = intersection.getPosition().getZ();

                    if (distance >= 0 &&
                            (closestIntersection == null || distance < closestIntersection.getDistance()) &&
                            (clippingPlanes == null || (intersectionZ >= clippingPlanes[0] && intersectionZ <= clippingPlanes[1]))) {
                        closestIntersection = intersection;
                    }
                }
            }
        }

        return closestIntersection;
    }

    /**
     * Calculates the refracted direction of a ray passing through a surface.
     *
     * @param incident The incident ray direction.
     * @param normal The normal of the surface at the intersection point.
     * @param refractiveIndexFrom The refractive index of the medium the ray is coming from.
     * @param refractiveIndexTo The refractive index of the medium the ray is entering.
     * @return The refracted direction, or null if total internal reflection occurs.
     */
    public static Vector3D refract(Vector3D incident, Vector3D normal, double refractiveIndexFrom, double refractiveIndexTo) {
        double eta = refractiveIndexFrom / refractiveIndexTo;
        double cosI = -Vector3D.dotProduct(normal, incident);
        double sinT2 = eta * eta * (1.0 - cosI * cosI);

        if (sinT2 > 1.0) {
            return null; // Total internal reflection
        }

        double cosT = Math.sqrt(1.0 - sinT2);
        return Vector3D.add(Vector3D.scalarMultiplication(incident, eta), Vector3D.scalarMultiplication(normal, eta * cosI - cosT));
    }
}
