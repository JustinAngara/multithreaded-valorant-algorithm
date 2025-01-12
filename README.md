
# Interactive Valorant colorbot

This Java/Python program actively scans a 10x10 boundary at the center of the screen. If there were to be an 'purple' enemy that ends up in sight, the Java program will run a python script that can bypass Vanguard to fire at the enemy. I have used Java in order to search the 10x10 pixel boundary for maximum efficiency.

# Details
I used low-level methods and hooks, multi threaded proccesses, and other algorithms in order to maximize efficiency that differentiates itself compared to other programs.
* Able to scan 100 pixel's and their color content within 3-5ms (significantally faster compared to other Python colorbots). Compared to many python programs, it would take roughly 700-800ms (over 100x slower) to analyze 100 pixels due to the lack of low level capabilties   
* Takes O(N) time-complexity to manage the 2d pixel content rather an O(N^2), ultimately limiting the amount of iterations to search for enemys

# Components
I have created this project by using multiple threads by pooling them together, and assign CPU usage to certain threads. For instance, I have created a thread strictly to analyze for the pixels within that 10x10 boundary. The search algorithm uses memorybitmaps for fast analysis compared to the window's counterpart GetPixel() method. If I were to use this method, each internal iteration of searching from one pixel to another would be ~6ms, and would culamatively take ~200ms to search the 10x10 grid. 
1. Searching for a portion of screen is a bit tricky on the low-level end. I had to use libraries such as GDI32 and User32 to interact with the windows graphic system.
2. Given that region, I use BitBlt to copy the pixels from the screen to memory and then store the data into an arraythrough the GetDIBits to the pixel array
3. Next, iterate through the 1d array and then convert to RGB values and check if they fit within the purple color
4. If it hits, send a command to the existing python processes
5. If there are multiple shots within a timeframe, there will be a delay between each shot to maintain accuracy
6. If you move, the triggerbot will turn off to maintain accuracy
7. If you shoot (manually), the triggerbot turns off to maintain consistency 
4. Other MISC processes such as listening if you're innacurate (you moved) or restarting the python processes occur to maintain security against Vanguard, and manage memory efficiently
