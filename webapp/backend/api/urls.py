from django.conf.urls import url, include
from django.urls import path
from . import views


urlpatterns = [
    path('users', views.UserListView.as_view()),
    path('users/<int:pk>', views.UserDetailView.as_view()),
    path('tracks', views.TrackView.as_view()),
    path('events', views.EventView.as_view()),
    path('events/<int:pk>', views.EventDetailView().as_view()),
    url(r'^auth/', include('rest_auth.urls')),
]
