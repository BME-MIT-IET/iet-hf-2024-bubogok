from os import environ
environ['PYGAME_HIDE_SUPPORT_PROMPT'] = '1'

import sys, pygame

pygame.init()

size = width, height = 800, 800

screen = pygame.display.set_mode(size)

class Vehicle(pygame.sprite.Sprite):
	def __init__(self, pos, typee):
		super().__init__()
		self.pos = pos
		self.color = pygame.Color(50, 50, 50)
		if(typee == "ally"):
			self.color = pygame.Color(0, 255, 0)
		elif(typee == "enemy"):
			self.color = pygame.Color(255, 0, 0)		
		
	def update(self):
		pass
	
	def show(self):
		pygame.draw.rect(screen, self.color, (self.pos[0] - 10, self.pos[1] - 10, self.pos[0] + 10, self.pos[1] + 10), width=0, border_radius=0)
		


v1 = Vehicle((400, 400), "ally")




while True:
	for event in pygame.event.get():
		if event.type == pygame.QUIT: sys.exit()
        
	screen.fill(pygame.Color(0, 0, 0))
	v1.show()
	pygame.display.flip()

