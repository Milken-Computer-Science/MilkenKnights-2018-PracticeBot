from plotly import __version__
from plotly.offline import download_plotlyjs, init_notebook_mode, plot, iplot
import plotly.plotly as py
import plotly.graph_objs as go
import plotly.figure_factory as FF

import numpy as np
import pandas as pd

lf = pd.read_csv('CenterAutoPathleft.csv')
rf = pd.read_csv('CenterAutoPathright.csv')

lptrace = go.Scatter(x=lf['DeltaTime'], y=lf['Position'],mode='lines', name='Position')
lvtrace = go.Scatter(x=lf['DeltaTime'], y=lf['Velocity'],mode='lines', name='Velocity')
latrace = go.Scatter(x=lf['DeltaTime'], y=lf['Acceleration'],mode='lines', name='Acceleration')

rptrace = go.Scatter(x=rf['DeltaTime'], y=rf['Position'],mode='lines', name='Position')
rvtrace = go.Scatter(x=rf['DeltaTime'], y=rf['Velocity'],mode='lines', name='Velocity')
ratrace = go.Scatter(x=rf['DeltaTime'], y=rf['Acceleration'],mode='lines', name='Acceleration')

lpathtrace = go.Scatter(x=lf['X'], y=lf['Y'], mode='lines', name='Left Path')
rpathtrace = go.Scatter(x=rf['X'], y=rf['Y'], mode='lines', name='Right Path')


llayout = go.Layout(title='Left Motion Profile', plot_bgcolor='rgb(230, 230,230)')
rlayout = go.Layout(title='Right Motion Profile', plot_bgcolor='rgb(230, 230,230)')
pathlayout = go.Layout(title='Robot Path', plot_bgcolor='rgb(230, 230,230)')

lprofilefig = go.Figure(data=[lptrace,lvtrace,latrace], layout=llayout)
rprofilefig = go.Figure(data=[rptrace,rvtrace,ratrace], layout=rlayout)

pathfig = go.Figure(data=[lpathtrace,rpathtrace], layout=pathlayout)

# Plot data in the notebook
plot(lprofilefig, filename='Left Motion Profile')
plot(rprofilefig, filename='Right Motion Profile')
plot(pathfig, filename='Robot Path')