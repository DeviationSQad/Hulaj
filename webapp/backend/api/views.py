from rest_framework import generics
from .serializers import UserSerializer, EventSerializer, PostSerializer, TrackSerializer
from .models import User, Event, Post, Track


class UserListView(generics.ListCreateAPIView):
    serializer_class = UserSerializer
    queryset = User.objects.all()


class UserDetailView(generics.RetrieveUpdateDestroyAPIView):
    serializer_class = UserSerializer
    queryset = User.objects.all()


class TrackView(generics.ListCreateAPIView):
    serializer_class = TrackSerializer

    def get_queryset(self):
        user = self.request.user.pk
        return Track.objects.filter(id_user=user)


class EventView(generics.ListCreateAPIView):
    serializer_class = EventSerializer
    queryset = Event.objects.all()


class EventDetailView(generics.RetrieveUpdateDestroyAPIView):
    serializer_class = EventSerializer
    queryset = Event.objects.all()
