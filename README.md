# Os_Project
Virtual Memory Simulation

Project Overview
In this project, you will build a simple but fully functional demand paged virtual memory component of an OS. Your implementation should include Page table, MMU and OS components. 

The Page Table
The page table has four fields in each page table entry:
•	A Boolean Valid indicating if the page of that index is in RAM.
•	An integer Frame giving the frame number of the page in RAM.
•	A Boolean Dirty indicating if the page has been written to.
•	Any other flags that you may fide useful

The MMU
This memory management unit simulator takes several arguments:
•	The number of pages in the process.
•	A reference string of memory accesses, each of the form <mode><page>, e.g., W4 is a write to page 4.
For each memory access, the MMU:
•	Checks if the page is in RAM. 
•	If not in RAM simulate a page fault by signaling the OS
•	Wait for the OS to indicate the page is loaded (the OS can simulate the load process using a delay)
•	If the access is a write access, sets the Dirty bit.

The OS
The OS simulator must take two arguments:
•	The number of pages in the process.
•	The number of frames allocated to the process.
 When the OS receives a page fault signal, it must:
•	Find which page is requested in which process 
•	If there is a free frame allocate the next one to the page.
•	If there are no free frames, choose a victim page.
o	If the victim page is dirty, simulate writing the page to disk by A delay and increment the counter of disk accesses.
o	Update the page table to indicate that the victim page is no longer Valid.
•	Simulate the page load by sleep(1) and increment the counter of disk accesses.
•	Update the page table to indicate that the page is Valid, in the allocated Frame, not Dirty,
•	Signal the MMU that the page was loaded


Once your system is working correctly, you will evaluate the performance of several page replacement algorithms on a selection of simple programs across a range of memory sizes. You will write a short report that explains the experiments, describes your results, 

Input File example
30    // this is the size of physical memory in frames 
3 // max frames per process
11// number of processes 
// for each process line starts with number of pages in the process  followed by memory access reference list
5 W0 R1 R2 R0 R3 W1 R2 R3 R4 R1 R2 W4 R1 R2 R0 R1






