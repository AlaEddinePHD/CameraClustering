# Camera Clustering
## Ascending Hierarchical Classification for Camera Clustering Based on FoV Overlaps.

The objective of this application is to group cameras in the Wireless Multimedia Sensors Networks (WMSN) according to their field of view (FoV). Our system begins by searching for all the polygons created by the intersection of the fields of view of two cameras. Based on the generated surfaces, an ascending hierarchical classification is applied to group the cameras with strongly overlapping visions fields.

# Programming Language

The application is developed in ***JAVA*** language with ***Eclipse*** (*Mars*) and ***JDK*** 1.7.0_79

# How the application works ?

- First, download the project, then import it into the eclipse.
- To launch the application, run the *MainPrincipale* class of *InterfaceSimulation* package

- You will see a window with buttons at the bottom

- Click on the first button (*Add Camera*) to add a new camera in the middle of the window

- To change the location of a camera or delete it, click on the second button (*Update Camera*), select one of the functions that appear in the menu :

	- For the "***Translation***" function : choose the corresponding camera identifier, input the translation vector. Click on the save then refresh button to redraw the cameras with the new coordinates.
	- For the "***Rotation***" function : choose the corresponding camera identifier then input the rotation angle. Click on the save then refresh button.
	- For the "***Remove***" function : choose the corresponding camera identifier. Click on the save then refresh button.
- Once you have finished drawing the network, click the (*Save Cameras*) button to save the coordinates of the cameras installed in a text file named "*Cameras_list.txt*".
- To group the cameras, first, click on the button (*Trace the Fov*) to find the intersection polygons between camera fields of view (overlaps). The results of this function are the overlapping surface matrix which it saves in a temporal file named "*Surface Matrix.txt*".

> NB 01: Refresh the project to see the file "*Surface Matrix.txt*".

> NB 02: be careful to delete the file "*Surface Matrix.txt*" because it is necessary for the clustring function.

-  After this step, click on the (*Clustering*) button to see the clustering result

> NB 03: The cameras of the same colors mean they belong to the same cluster.

- In the project there is a scenario that contains a set of cameras randomly positioned. To test this scenario click on the button (Open cameras) and it will display the network for you.
