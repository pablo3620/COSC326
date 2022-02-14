from sys import stdin
import cairo

# scales = [1, 0.8, 0.1]
# colors = [[255, 0, 0],
#           [0, 255, 0],
#           [0, 0, 255]]
MIDDLE = 500
    

def createSqaure(middleX, middleY, layer, context): 

    drawStack[layer].append([middleX-MIDDLE*scales[layer], middleY-MIDDLE*scales[layer],2*MIDDLE*scales[layer], 2*MIDDLE*scales[layer]])
    if layer < layerCount-1:
        createSqaure(middleX-MIDDLE*scales[layer], middleY-MIDDLE*scales[layer], layer + 1, context)
        createSqaure(middleX-MIDDLE*scales[layer], middleY+MIDDLE*scales[layer], layer + 1, context)
        createSqaure(middleX+MIDDLE*scales[layer], middleY-MIDDLE*scales[layer], layer + 1, context)
        createSqaure(middleX+MIDDLE*scales[layer], middleY+MIDDLE*scales[layer], layer + 1, context)



scales = []
colors = []
for line in stdin: 
    try:
        tokens=line.split()
        scales.append(float(tokens[0]))
        colors.append([int(token)/255 for token in tokens[1:]])
        if len(colors[-1]) != 3:
            raise Exception()
    except Exception:
        print("bad line: " + line)
        exit()



layerCount = len(scales)

drawStack = [[] for i in range(layerCount)]


with cairo.SVGSurface("quilt.svg", MIDDLE*2, MIDDLE*2) as surface:
    scales[:]  = [i / (sum(scales)*1.1) for i in scales]
    # print(scales)
    context = cairo.Context(surface)
    createSqaure(MIDDLE,MIDDLE, 0, context)

    for layerIdx, layer in enumerate(drawStack):
        for square in layer:
            # setting color of the context
            # print(layer)
            # print(colors[layer])
            context.set_source_rgb(*colors[layerIdx])
            # creating a rectangle
            context.rectangle(*square)
            # Fill the color inside the rectangle
            context.fill()
# printing message when file is saved
print("File Saved")


