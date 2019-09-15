from rest_framework import generics
from rest_framework.parsers import FileUploadParser
from .serializers import UserSerializer, EventSerializer, PostSerializer, TrackSerializer, ScooterSerializer, CarSerializer
from .models import User, Event, Post, Track, Scooter, Car


class UserListView(generics.ListCreateAPIView):
    parser_class = (FileUploadParser,)
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


class ScooterView(generics.ListCreateAPIView):
    parser_class = (FileUploadParser,)
    serializer_class = ScooterSerializer

    def get_queryset(self):
        user = self.request.user.pk
        return Scooter.objects.filter(id_user=user)


class ScooterDetailView(generics.RetrieveUpdateDestroyAPIView):
    serializer_class = ScooterSerializer
    queryset = Scooter.objects.all()


class CarView(generics.ListCreateAPIView):
    serializer_class = CarSerializer

    def get_queryset(self):
        user = self.request.user.pk
        return Car.objects.filter(id_user=user)


class CarDetailView(generics.RetrieveUpdateDestroyAPIView):
    serializer_class = CarSerializer
    queryset = Car.objects.all()
