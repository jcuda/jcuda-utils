# jcuda-utils

Utility classes for JCuda.

NOTE: These classes have not been updated for a while. They originally have 
been contained in the utlities package at 
http://jcuda.org/utilities/utilities.html.
Most of them are Java ports of the functions that have been contained in the 
`cutil.h` header of the CUDA SDK up to CUDA 5.0, which are now contained, in 
a similar form, in the `samples/common` headers of the CUDA SDK. 

The most important class in this library is the `KernelLauncher` class, which 
significantly simplified the kernel invocation in CUDA versions up to version 
4.0. With the `cuLaunchKernel` function that was introduced in CUDA 4.0, the 
kernel invocation now is far simpler, but the `KernelLauncher` class still 
allows to call kernels more conveniently. 

The `KernelLauncher` had the additional benefit of allowing to directly 
compile source code that was given as a `String` by invoking the NVCC in the 
backround. With the introduction of the 
[NVRTC](http://docs.nvidia.com/cuda/nvrtc/index.html) in CUDA 7.5, it is 
now also possible to do a real, pure runtime compilation of source code 
with plain CUDA API calls. The `KernelLauncher` may be updated in the 
future to reflect these extensions. 

